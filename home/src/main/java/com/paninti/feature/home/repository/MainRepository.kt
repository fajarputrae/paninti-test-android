package com.paninti.feature.home.repository

import com.paninti.feature.home.model.MainDataSource
import com.paninti.lib.network.BaseRepository
import com.paninti.lib.network.util.NetworkStateManager

class MainRepository(
    private val dataSource: MainDataSource,
    stateManager: NetworkStateManager
) : BaseRepository(stateManager) {

    suspend fun getAllNations() = dataSource.getAll().awaitDataNetwork()

}