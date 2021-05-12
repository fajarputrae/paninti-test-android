package com.paninti.feature.home.di

import com.paninti.feature.home.MainViewModel
import com.paninti.feature.home.model.MainDataSource
import com.paninti.feature.home.repository.MainRepository
import com.paninti.lib.network.di.serverUrl
import com.paninti.lib.network.network.createApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val remoteModule = module {
    single {
        createApi<MainDataSource>(
            get(),
            serverUrl
        )
    }
}

private val repositoryModule = module {
    single { MainRepository(get(), get()) }
}

private val viewModel = module {
    viewModel { MainViewModel(get()) }
}


val mainModule = listOf(remoteModule, repositoryModule, viewModel)