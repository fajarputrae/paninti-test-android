package com.paninti.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paninti.feature.home.repository.AllResponse
import com.paninti.feature.home.repository.MainRepository
import com.paninti.lib.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

    private val _getAllNationsSuccess = MutableLiveData<StateAllNations.AllNationsSuccess>()
    val getAllNationsSuccess: LiveData<StateAllNations.AllNationsSuccess> = _getAllNationsSuccess


    fun getAllNations() {
        launch {
            showLoading()
            repository.getAllNations().onResultSuccess { nationsModel ->
                nationsModel?.let {
                    _getAllNationsSuccess.value = StateAllNations.AllNationsSuccess(nationsModel)
                }
            }
            hideLoading()
        }
    }


    sealed class StateAllNations {
        data class AllNationsSuccess(val data: AllResponse) : StateAllNations()
    }


}