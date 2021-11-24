package com.example.services

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

class ErrorInterceptor : Interceptor {

    @Throws(Throwable::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var returnResponse: Response? = null
        var msg: String? = null

        try {
            returnResponse = chain.proceed(chain.request())
            if (!returnResponse.isSuccessful) {
                val body = returnResponse.body
                val source = body?.source()
                source?.request(Long.MAX_VALUE)
                val buffer = source?.buffer
                val charset = body?.contentType()?.charset(Charset.forName("UTF-8"))
                charset?.let {
                    val json = buffer?.clone()?.readString(it)
                    val jsonElement = JsonParser().parse(json)
                    if (jsonElement is JsonObject) {
                        if (jsonElement.has("title")) {
                            msg = jsonElement["title"]?.asString
                        } else if (jsonElement.has("message")) {
                            msg = jsonElement["message"]?.asString
                        }
                    }
                }
                if (msg != null)
                    throw ErrorObject(returnResponse.code, msg)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw ErrorObject(returnResponse?.code, msg ?: e.message)
        }
        return returnResponse
    }
}