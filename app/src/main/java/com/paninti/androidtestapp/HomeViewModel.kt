package com.paninti.androidtestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paninti.androidtestapp.repository.AllResponse
import com.paninti.androidtestapp.repository.FilterType
import com.paninti.androidtestapp.repository.HomeRepository
import com.paninti.lib.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: HomeRepository) : BaseViewModel() {

    private val _getAllNationsSuccess = MutableLiveData<StateAllNations.AllNationsSuccess>()
    val getAllNationsSuccess: LiveData<StateAllNations.AllNationsSuccess> = _getAllNationsSuccess
    private val _setSelectedFilter = MutableLiveData<StateFilter.UpdateSelectedSuccess>()
    val selectFilterSuccess: LiveData<StateFilter.UpdateSelectedSuccess> = _setSelectedFilter

    private fun setSelectedFilter(filterType: ArrayList<FilterType>) {
        _setSelectedFilter.value = StateFilter.UpdateSelectedSuccess(filterType)
    }

    fun getAllNations() {
        launch {
            repository.getAllNations().onResultSuccess { nationsModel ->
                nationsModel?.let {
                    _getAllNationsSuccess.value = StateAllNations.AllNationsSuccess(nationsModel)
                }
            }
        }
    }

    fun setFilter(){
        val filterList = arrayListOf<FilterType>()
        filterList.add(FilterType("Largest Population"))
        filterList.add(FilterType("Smallest Population"))
        filterList.add(FilterType("Africa"))
        filterList.add(FilterType("Americas"))
        filterList.add(FilterType("Asia"))
        filterList.add(FilterType("Europe"))
        filterList.add(FilterType("Oceania"))

        setSelectedFilter(filterList)
    }

    fun updateSelectedFilter(selectedFilter: ArrayList<FilterType>){
        setSelectedFilter(selectedFilter)
    }


    sealed class StateAllNations {
        data class AllNationsSuccess(val data: ArrayList<AllResponse>) : StateAllNations()
    }

    sealed class StateFilter {
        data class UpdateSelectedSuccess(val data: ArrayList<FilterType>) : StateFilter()
    }
}