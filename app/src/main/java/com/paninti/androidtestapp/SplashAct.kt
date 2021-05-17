package com.paninti.androidtestapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.paninti.lib.base.activity.BaseAct

class SplashAct : BaseAct() {
    private var mDelayHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler.postDelayed(mRunnable, 2000)

    }

    override fun onResume() {
        mDelayHandler.postDelayed(mRunnable, 2000)
        super.onResume()
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity(Intent(this@SplashAct, MainActivity::class.java))
            finish()
        }
    }

    public override fun onDestroy() {
        mDelayHandler.removeCallbacks(mRunnable)
        super.onDestroy()
    }
}