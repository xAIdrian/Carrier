package com.example.carrier.domain

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.example.carrier.domain.local.ShiftDatabaseContract
import com.example.carrier.domain.local.ShiftDbHelper
import com.example.carrier.domain.service.CarrierShiftService
import com.example.carrier.domain.service.RetrofitServiceBuilder.Companion.SUCCESS_CODE
import com.example.carrier.model.CarrierShift
import com.example.carrier.model.CarrierShiftResponse
import com.example.carrier.model.MessageBody
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CarrierRepository @Inject constructor(
    private val carrierService: CarrierShiftService,
    databaseHelper: ShiftDbHelper
) {
    private val writableDb = databaseHelper.writableDatabase
    private val readableDb = databaseHelper.readableDatabase

    fun getShifts(id: Int = 1) =
        carrierService.getShift(id)
            .map { response ->
                var passingResponse = response

                val dbShiftStatus = getShiftStatusFromDatabase(response)
                if (dbShiftStatus != null) {
                    val newStateSynced = response.shift.copy(shiftStatus = dbShiftStatus)
                    passingResponse = response.copy(shift = newStateSynced)
                } else {
                    //adding data to database and passing updated response
                    if (response.status == SUCCESS_CODE) {
                        passingResponse = mapStatusToDatabase(response)
                    }
                }
                passingResponse
            }.subscribeOn(Schedulers.io())

    /**
     * Define a projection that specifies which columns from the database.
     * Filter results
     */
    private fun getShiftStatusFromDatabase(response: CarrierShiftResponse): CarrierShift.Status? {
        val projection = arrayOf(
            BaseColumns._ID,
            ShiftDatabaseContract.ShiftEntry.COLUMN_NAME_STATUS
        )

        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(response.shift.id.toString())

        val cursor = readableDb.query(
            ShiftDatabaseContract.ShiftEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val idStatus = getStatusForId(cursor)
        return if (idStatus >= 0) CarrierShift.Status.fromInt(idStatus) else null
    }

    /**
     * the cursor starts at position -1, calling moveToNext() places the "read position" on the
     * first entry in the results and returns whether or not the cursor is already past the last
     * entry in the result set.
     */
    private fun getStatusForId(cursor: Cursor): Int {
        with(cursor) {
            return if (moveToFirst()) {
                getLong(getColumnIndexOrThrow(BaseColumns._ID)).toInt()
            } else {
                -1
            }
        }
    }

    /**
     * We insert our status to the database as our source of truth and sync our object with
     * the key stored to our database for guaranteed access in the future
     */
    private fun mapStatusToDatabase(shiftResponse: CarrierShiftResponse): CarrierShiftResponse {
        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(BaseColumns._ID, shiftResponse.shift.id)
            put(ShiftDatabaseContract.ShiftEntry.COLUMN_NAME_STATUS, shiftResponse.shift.shiftStatus.ordinal)
        }
        // Insert the new row, returning the primary key value of the new row
        val newRowId = writableDb?.insert(ShiftDatabaseContract.ShiftEntry.TABLE_NAME, null, values)
        return if (newRowId != null && newRowId >= 0) {
            val newIdSyncedShift = shiftResponse.shift.copy(id = newRowId.toInt())
            shiftResponse.copy(shift = newIdSyncedShift)
        } else {
            shiftResponse
        }
    }

    fun postMessage(
        id: Int = 1,
        messageBody: MessageBody = MessageBody("Hello, World!")
    ) = carrierService.postShiftMessage(id, messageBody).subscribeOn(Schedulers.io())
}