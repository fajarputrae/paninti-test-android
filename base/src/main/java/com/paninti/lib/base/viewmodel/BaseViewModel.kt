package com.paninti.lib.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paninti.lib.base.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import com.paninti.lib.network.result.Result

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val _loadingStatus = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _loadingStatus

    protected val _message = SingleLiveEvent<Message>()
    val message: LiveData<Message> = _message

    protected val _noInternetConnection = MutableLiveData<Boolean>()
    val noInternetConnection: LiveData<Boolean> = _noInternetConnection

    protected val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun showLoading() {
        _loadingStatus.value = true
    }

    protected fun hideLoading() {
        if (_loadingStatus.value == true) {
            _loadingStatus.value = false
        }
    }

    protected fun <T> responseHelper(result: Result<T>) {
        _noInternetConnection.value = false
        when (result) {
            is Result.NoNetwork -> {
                _noInternetConnection.value = true
            }
            is Result.Error -> {
                _message.value = Message.Dialog(result.response.message)
            }
            is Result.Exception -> {
                _message.value = Message.Dialog(result.exception.message)
            }

        }
    }

    protected fun <T> Result<T>.onResultSuccess(result: (T?) -> Unit) {

        when (this) {
            is Result.Ok -> {
                result.invoke(value)
            }

            is Result.ErrorCode -> {
                erroStatusCode(code, message)
            }

            is Result.NoNetwork -> {
                _noInternetConnection.value = true
                _message.value = Message.Dialog("No Internet Access")
            }
            is Result.Error -> {
                _message.value = Message.Dialog(response.message)
            }
            is Result.Exception -> {
                _message.value = Message.Dialog(exception.message)
            }

        }
    }

    open fun erroStatusCode(status: Int, msg: String) {
        _message.value = Message.Dialog(msg)
    }

}