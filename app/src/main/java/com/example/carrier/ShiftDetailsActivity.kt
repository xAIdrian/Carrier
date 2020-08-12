package com.example.carrier

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ShiftDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        bindViews()
    }

    private fun initView() {
        setContentView(R.layout.activity_shift_details)
    }

    private lateinit var statusView: TextView
    private lateinit var driverIdView: TextView
    private lateinit var driverNameView: TextView
    private lateinit var sendHelloButton: Button

    private fun bindViews() {
        statusView = findViewById(R.id.activity_shift_details_status)
        driverIdView = findViewById(R.id.activity_shift_details_driver_id)
        driverNameView = findViewById(R.id.activity_shift_details_driver_name)
        sendHelloButton = findViewById(R.id.activity_shift_details_button_hello)
    }
}
