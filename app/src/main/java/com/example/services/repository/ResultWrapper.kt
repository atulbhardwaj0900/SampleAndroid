package com.example.services.repository

import com.example.services.ErrorObject
import retrofit2.HttpException
import java.io.IOException

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class GenericError(val code: Int? = null, val error: String? = null) : Result<Nothing>()
    object NetworkError : Result<Nothing>()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>): Result<T> =
    try {
        call.invoke()
    } catch (throwable: Throwable) {
        when (throwable) {
            is ErrorObject -> {
                Result.GenericError(throwable.code, throwable.msg)
            }
            is IOException -> Result.NetworkError
            is HttpException -> {
                val code = throwable.code()
                val errorMsg = throwable.message()
                Result.GenericError(
                    code,
                    errorMsg
                )
            }
            else -> Result.Error(
                IOException(
                    throwable.message,
                    throwable
                )
            )
        }
    }

enum class ErrorResponses {
    Network,
    Generic
}