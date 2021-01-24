package com.example.carrier.domain.service

import com.example.carrier.R
import com.example.carrier.common.ResourceProvider
import com.example.carrier.domain.service.RetrofitServiceBuilder.Companion.ERROR_CODE
import com.example.carrier.domain.service.RetrofitServiceBuilder.Companion.SUCCESS_CODE
import okhttp3.*
import java.net.URI
import javax.inject.Inject

class FakeInterceptor @Inject constructor(
    private val resourceProvider: ResourceProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Thread.sleep(2500)

        val uri: URI = chain.request().url().uri() // Get Request URI
        val parsedPath = uri.path.substring(1).split("/").toTypedArray()

        val statusCode: Int //statusCode is assigned in each condition so it can be declared immutable
        val responseString = when {
            parsedPath.size == 3 && parsedPath[0] == "shifts" && parsedPath[2] == "messages" -> {
                //all posts ids get the same response
                statusCode = SUCCESS_CODE
                "{\n" +
                        "  \"status\": 200,\n" +
                        "  \"message\": \"Successful Post\",\n" +
                        "  \"success\": true \n" +
                        "}"
            }
            parsedPath.size == 2 && parsedPath[0] == "shifts" && parsedPath[1] == "1" -> {
                //we are getting the only carrier shift in the application
                statusCode = SUCCESS_CODE
                resourceProvider.getRawResource(R.raw.shift_details_response)
                    .bufferedReader()
                    .use {
                        it.readText()
                    }
            }
            parsedPath.size == 2 && parsedPath[0] == "shifts" && parsedPath[1].toInt() > 1 -> {
                statusCode = ERROR_CODE
                "{\n" +
                        "  \"status\": 400,\n" +
                        "  \"message\": \"Cannot find shift associated with this ID\"\n" +
                        "}"
            }
            else -> {
                //catching everything else
                statusCode = ERROR_CODE
                "{\n" +
                        "  \"status\": 400,\n" +
                        "  \"message\": \"Malformed request\"\n" +
                        "}"
            }
        }
        return Response.Builder()
            .code(statusCode)
            .message(responseString)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    responseString
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}
