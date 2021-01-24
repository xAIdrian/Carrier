package com.example.carrier.domain

import com.example.carrier.domain.local.ShiftDao
import com.example.carrier.domain.service.CarrierShiftService
import com.example.carrier.domain.service.RetrofitServiceBuilder.Companion.SUCCESS_CODE
import com.example.carrier.model.CarrierShiftResponse
import com.example.carrier.model.MessageBody
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class CarrierRepository @Inject constructor(
    private val carrierService: CarrierShiftService,
    private val shiftDao: ShiftDao
) {

    fun getShifts(id: Int = 1) =
        carrierService.getShift(id)
            .map { response ->
                var passingResponse = response

                val dbShiftStatus = shiftDao.getShiftStatusFromDatabase(response.shift.id)
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
     * We insert our status to the database as our source of truth and sync our object with
     * the key stored to our database for guaranteed access in the future
     */
    private fun mapStatusToDatabase(shiftResponse: CarrierShiftResponse): CarrierShiftResponse {
        val newRowId = shiftDao.insertShiftToDatabase(shiftResponse.shift)
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