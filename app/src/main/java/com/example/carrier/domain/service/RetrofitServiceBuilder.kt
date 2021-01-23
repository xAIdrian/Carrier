package com.example.carrier.domain.service

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitServiceBuilder @Inject constructor(
    private val stubInterceptor: FakeInterceptor
) {
    private var carrierShiftService: CarrierShiftService? = null

    private val retorfitBuilder = Retrofit.Builder()
        .baseUrl(GITHUB_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit = retorfitBuilder.build()
    private val httpClient = OkHttpClient.Builder()

    fun getClient(): CarrierShiftService {
        if (!httpClient.interceptors().contains(stubInterceptor)) {
            httpClient.addInterceptor(stubInterceptor)
            retorfitBuilder.client(httpClient.build())
            retrofit = retorfitBuilder.build()
        }
        return carrierShiftService ?: retrofit.create(CarrierShiftService::class.java)
    }

    companion object {
        const val GITHUB_URL = "https://example.haulhub.com/"

        const val SUCCESS_CODE = 200
        const val ERROR_CODE = 400
    }
}