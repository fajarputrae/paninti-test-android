package com.paninti.lib.network

import com.paninti.lib.network.model.BaseDataModel
import com.paninti.lib.network.network.awaitDataResult
import com.paninti.lib.network.network.awaitResult
import com.paninti.lib.network.util.NetworkStateManager
import retrofit2.Call
import com.paninti.lib.network.result.Result

open class BaseRepository(private val networkStateManager: NetworkStateManager) {

    protected suspend fun <DATA : Any, T : BaseDataModel<DATA>> Call<T>.awaitNetwork(): Result<DATA> {
        return if (networkStateManager.isOnline()) {
            this.awaitResult()
        } else {
            Result.NoNetwork(Exception("No Internet"))
        }
    }

    protected suspend fun <DATA : Any, T : DATA> Call<T>.awaitDataNetwork(): Result<DATA> {
        return if (networkStateManager.isOnline()) {
            this.awaitDataResult()
        } else {
            Result.NoNetwork(Exception("No Internet"))
        }
    }
}