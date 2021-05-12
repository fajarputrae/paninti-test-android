package com.paninti.lib.network.di

import com.paninti.lib.network.BuildConfig
import com.paninti.lib.network.network.createOkHttpClient
import com.paninti.lib.network.util.NetworkStateManager
import com.paninti.lib.network.util.NetworkStateManagerImp
import org.koin.dsl.module

const val serverUrl = BuildConfig.BASE_URL

private val remoteModule = module {
    single { createOkHttpClient() }
}

private val networkUtil = module {
    single { NetworkStateManagerImp(get()) as NetworkStateManager }
}

val networkModule = listOf(networkUtil, remoteModule)