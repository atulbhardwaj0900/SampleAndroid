package com.example.services

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val context: Context) : Interceptor {

    @Throws(Throwable::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val returnResponse: Response?
        val msg: String?

        try {
            val builder = chain.request().newBuilder()
            returnResponse = chain.proceed(builder.build())

            if (!NetworkUtils.isOnline(context)) {
                msg = "No connection"
                Log.e("NetworkInterceptor", msg)
                throw ErrorObject(0, msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw ErrorObject(0, "No connection")
        }

        return returnResponse
    }
}