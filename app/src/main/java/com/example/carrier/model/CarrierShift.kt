package com.example.carrier.model

import com.google.gson.annotations.SerializedName
import java.sql.Types
import java.util.*

/**
 * The information received from the stubbed GET API call should always be used
 */
data class CarrierShift (
    val id: Int,
    @SerializedName("status")
    val shiftStatus: Status,
    @SerializedName("driver_id") val driverId: Int,
    @SerializedName("driver_name") val driverName: String
) {
    /**
     *  except for the shift status.
     *  The status in the response may not be current due to asynchronous background processing.
     *  If there is a matching shift stored locally, the status that's stored locally should be
     *  used instead of what's returned by the API.
     *  The local data is the source of truth.
     */
    enum class Status(val value: Int) {
        @SerializedName("scheduled")
        SCHEDULED(0),
        @SerializedName("started")
        STARTED(1),
        @SerializedName("finished")
        FINISHED(2);

        // Keeping lint errors to save time and keep from using experimental APIs
        override fun toString() = name.toLowerCase(Locale.ROOT).capitalize()

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }
    }
}