package com.example.carrier.domain.service

import com.example.carrier.model.CarrierShiftResponse
import com.example.carrier.model.MessageBody
import com.example.carrier.model.PostMessageResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CarrierShiftService {
    @GET("shifts/{id}")
    fun getShift(
        @Path("id") id: Int
    ): Single<CarrierShiftResponse>

    @POST("shifts/{id}/messages")
    fun postShiftMessage(
        @Path("id") id: Int,
        @Body message: MessageBody
    ): Single<PostMessageResponse>
}