package com.paninti.lib.network.util

import android.content.Context
import android.net.ConnectivityManager

interface NetworkStateManager {
    fun isOnline(): Boolean
}

class NetworkStateManagerImp(context: Context) : NetworkStateManager {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isOnline(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}