package com.example.carrier.domain.local

import android.provider.BaseColumns

/**
 * format = (column,type)
 *
 * id, primary key
 * status, int
 *
 * possible statuses are:
 *  0 - scheduled
 *  1 - started
 *  2 - finished
 *
 *  This class explicitly specifies the layout of your schema in a systematic and self-documenting way.
 */
object ShiftDatabaseContract {
    object ShiftEntry : BaseColumns {
        const val TABLE_NAME = "CarrierShifts"
        const val COLUMN_NAME_STATUS = "status"
    }

    //create and maintain the database and tables
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ShiftEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ShiftEntry.COLUMN_NAME_STATUS} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ShiftEntry.TABLE_NAME}"
}