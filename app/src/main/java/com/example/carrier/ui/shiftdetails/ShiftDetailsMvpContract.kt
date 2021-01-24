package com.example.carrier.ui.shiftdetails

import com.example.carrier.model.CarrierShift

/**
 * Interfaces help with decoupling the parts of the app.
 * The interface forms a contract between the presenter and view.
 */
interface ShiftDetailsMvpContract {
    interface Presenter {
        fun getShifts()
        fun sendHello()
    }

    interface View {
        fun showCarrierShift(shift: CarrierShift)
        fun errorMessage(messageString: String)
        fun showLoading(isVisible: Boolean)
        fun showSimpleLoading(isVisible: Boolean)
        fun postSuccess()
    }
}