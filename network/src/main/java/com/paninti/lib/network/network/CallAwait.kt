package com.paninti.lib.network.network

import com.paninti.lib.network.model.BaseDataModel
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import com.paninti.lib.network.result.Result

suspend fun <DATA : Any, T : BaseDataModel<DATA>> Call<T>.awaitResult(): Result<DATA> {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>) {
                continuation.resume(
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body == null) {
                            Result.Exception(NullPointerException("Response body is null"))
                        } else {
                            when (body.alert.code) {
                                200 -> Result.Ok(body.data, body.alert.message)
                                else -> Result.ErrorCode(body.alert.code, body.alert.message)
                            }
                        }
                    } else {
                        Result.ErrorCode(response.code(), response.message())
                    }
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
                continuation.resume(Result.Exception(t))
            }
        })

        registerOnCompletion(continuation)
    }
}

suspend fun <T : Any> Call<T>.awaitDataResult(): Result<T> {
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }
        enqueue(object : Callback<T?> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                if (response.isSuccessful) {
                    continuation.resume(Result.Ok(response.body()))
                } else {
                    continuation.resume(Result.Exception(HttpException(response)))
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                continuation.resume(Result.Exception(t))
            }
        })
    }
}

private fun Call<*>.registerOnCompletion(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        try {
            cancel()
        } catch (ex: Throwable) {
            //Ignore cancel exception
        }
    }
}