package com.paninti.androidtestapp.repository

import com.paninti.androidtestapp.model.HomeDataSource
import com.paninti.lib.network.BaseRepository
import com.paninti.lib.network.util.NetworkStateManager

class HomeRepository(
    private val dataSource: HomeDataSource,
    stateManager: NetworkStateManager
) : BaseRepository(stateManager) {

    suspend fun getAllNations() = dataSource.getAll().awaitDataNetwork()

}