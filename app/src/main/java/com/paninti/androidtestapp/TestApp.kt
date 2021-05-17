package com.paninti.androidtestapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.paninti.androidtestapp.di.homeModule
import com.paninti.lib.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class TestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupHawk()
        setupKoin()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupHawk() {
        Hawk.init(this).build()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@TestApp)
            modules(networkModule + homeModule)
        }
    }

}