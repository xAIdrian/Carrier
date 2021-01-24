package com.example.carrier.ui.shiftdetails

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.carrier.R
import com.example.carrier.common.MvpActivity
import com.example.carrier.model.CarrierShift
import javax.inject.Inject

class ShiftDetailsActivity : MvpActivity<ShiftDetailsPresenter, ShiftDetailsMvpContract.View>(), ShiftDetailsMvpContract.View {

    @Inject lateinit var shiftPresenter: ShiftDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.shiftDetailsActivityComponent().create().inject(this)

        initView()
        bindViews()

        shiftPresenter.getShifts()
    }

    private fun initView() {
        setContentView(R.layout.activity_shift_details)
    }

    private lateinit var loadingProgress: ProgressBar
    private lateinit var statusView: TextView
    private lateinit var driverIdView: TextView
    private lateinit var driverNameView: TextView
    private lateinit var sendHelloButton: Button

    private fun bindViews() {
        loadingProgress = findViewById(R.id.loading)
        statusView = findViewById(R.id.activity_shift_details_status)
        driverIdView = findViewById(R.id.activity_shift_details_driver_id)
        driverNameView = findViewById(R.id.activity_shift_details_driver_name)
        sendHelloButton = findViewById(R.id.activity_shift_details_button_hello)
        sendHelloButton.setOnClickListener {
            shiftPresenter.sendHello()
        }
    }

    override fun getPresenter(): ShiftDetailsPresenter = shiftPresenter

    override fun getMvpView(): ShiftDetailsMvpContract.View = this

    override fun showCarrierShift(shift: CarrierShift) {

        statusView.text = shift.shiftStatus.toString()
        driverIdView.text = shift.driverId.toString()
        driverNameView.text = shift.driverName
    }

    override fun errorMessage(messageString: String) {
        statusView.text = messageString

        statusView.visibility = View.VISIBLE
        driverIdView.visibility = View.INVISIBLE
        driverNameView.visibility = View.INVISIBLE
    }

    override fun showLoading(isVisible: Boolean) {
        if (isVisible) {
            loadingProgress.visibility = View.VISIBLE

            statusView.visibility = View.INVISIBLE
            driverIdView.visibility = View.INVISIBLE
            driverNameView.visibility = View.INVISIBLE
        } else {
            loadingProgress.visibility = View.GONE

            statusView.visibility = View.VISIBLE
            driverIdView.visibility = View.VISIBLE
            driverNameView.visibility = View.VISIBLE
        }
    }

    override fun showSimpleLoading(isVisible: Boolean) {
        loadingProgress.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun postSuccess() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_LONG).show()
    }
}
