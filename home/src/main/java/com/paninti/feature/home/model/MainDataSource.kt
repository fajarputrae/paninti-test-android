package com.paninti.feature.home.model

import com.paninti.feature.home.repository.AllResponse
import retrofit2.Call
import retrofit2.http.GET

interface MainDataSource {

    @GET("v2/all")
    fun getAll(): Call<AllResponse>

}