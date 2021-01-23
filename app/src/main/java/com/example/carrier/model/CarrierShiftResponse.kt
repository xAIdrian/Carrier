package com.example.carrier.model

data class CarrierShiftResponse (
    val status: Int,
    val message: String,
    val shift: CarrierShift
)