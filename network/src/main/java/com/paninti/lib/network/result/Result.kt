package com.paninti.lib.network.result

import okhttp3.Response
import retrofit2.HttpException

/**
 * Sealed class of HTTP result
 */
@Suppress("unused")
sealed class Result<out T : Any?> {
    /**
     * Successful result of request without errors
     */
    class Ok<out T : Any>(
        val value: T?,
        val message: String? = null
    ) : Result<T>() {
        override fun toString() = "Result.Ok{value=$value, response=$message}"
    }

    /**
     * HTTP error
     */
    class Error(
        override val exception: HttpException,
        override val response: Response
    ) : Result<Nothing>(),
        ErrorResult,
        ResponseResult {
        override fun toString() = "Result.Error{exception=$exception}"
    }

    /**
     * Error Code
     */
    class ErrorCode(
        val code: Int,
        val message: String
    ) : Result<Nothing>() {
        override fun toString() = "Result.TokenExpired{$message}"
    }

    /**
     * HTTP error
     */
    class NoNetwork(
        val exception: Throwable
    ) : Result<Nothing>() {
        override fun toString() = "No Network Available"
    }

    /**
     * Network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response
     */
    class Exception(
        val exception: Throwable
    ) : Result<Nothing>() {
        override fun toString() = "Result.Exception{$exception}"
    }

}

/**
 * Interface for [Result] classes with [okhttp3.Response]: [Result.Ok] and [Result.Error]
 */
interface ResponseResult {
    val response: Response
}

/**
 * Interface for [Result] classes that contains [Throwable]: [Result.Error] and [Result.Exception]
 */
interface ErrorResult {
    val exception: Throwable
}