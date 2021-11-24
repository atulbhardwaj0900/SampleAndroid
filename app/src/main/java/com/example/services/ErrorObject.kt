package com.example.services

import java.io.IOException

class ErrorObject(val code: Int?, val msg: String?) : IOException() {

    fun isUnknown(): Boolean {
        return code == UNKNOWN
    }

    fun isNoContent(): Boolean {
        return code == NO_CONTENT
    }

    fun isUnauthorized(): Boolean {
        return code == UNAUTHORIZED
    }

    fun isValidationError(): Boolean {
        return code == ERROR_VALIDATION
    }

    fun isTermsAndConditionsUpdated(): Boolean {
        return code == UNAUTHORIZED && msg == TERMS_CONDITIONS_UPDATED
    }

    fun isForbidden(): Boolean {
        return code == FORBIDDEN
    }

    fun isNotFound(): Boolean {
        return code == NOT_FOUND
    }

    fun isInternalServerError(): Boolean {
        return code == INTERNAL_SERVER_ERROR
    }

    fun isRoomEmptyResultSet(): Boolean {
        return code == ROOM_EMPTY_RESULT_SET
    }

    fun isNoConnection(): Boolean {
        return code == NO_CONNECTION
    }

    fun isStreamApiException(): Boolean {
        return code == STREAM_API_EXCEPTION
    }

    override fun toString(): String {
        return "ErrorObject(code=$code, msg=$msg)"
    }

    companion object {
        const val NO_CONNECTION = 0
        const val UNKNOWN = 1
        const val ROOM_EMPTY_RESULT_SET = 2
        const val NO_CONTENT = 204
        const val ERROR_VALIDATION = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
        const val TERMS_CONDITIONS_UPDATED = "expired terms of use agreement"
        const val STREAM_API_EXCEPTION = 1001
    }
}