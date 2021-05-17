package com.paninti.androidtestapp.di

import com.paninti.androidtestapp.HomeViewModel
import com.paninti.androidtestapp.model.HomeDataSource
import com.paninti.androidtestapp.repository.HomeRepository
import com.paninti.lib.network.di.serverUrl
import com.paninti.lib.network.network.createApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val remoteModule = module {
    single {
        createApi<HomeDataSource>(
            get(),
            serverUrl
        )
    }
}

private val repositoryModule = module {
    single { HomeRepository(get(), get()) }
}

private val viewModel = module {
    viewModel { HomeViewModel(get()) }
}


val homeModule = listOf(remoteModule, repositoryModule, viewModel)