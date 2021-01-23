package com.example.carrier.model

data class PostMessageResponse(
    val status: Int,
    val message: String,
    val success: Boolean
)