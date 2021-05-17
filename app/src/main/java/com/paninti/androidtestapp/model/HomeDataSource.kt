package com.paninti.androidtestapp.model

import com.paninti.androidtestapp.repository.AllResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeDataSource {

    @GET("v2/all")
    fun getAll(): Call<ArrayList<AllResponse>>

}