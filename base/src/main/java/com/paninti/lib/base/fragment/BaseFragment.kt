package com.paninti.lib.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.paninti.lib.base.activity.BaseAct
import com.paninti.lib.base.interfaceutil.IToolbar
import com.paninti.lib.base.viewmodel.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    open fun showMessage(message: Message) {
        if (activity is BaseAct) {
            (activity as BaseAct).showMessage(message)
        } else {
            throw Exception("Activity must inherit BaseActivity")
        }
    }


    protected fun showToolbar(title: String, backButton: Boolean = false) {
        setupToolbar(title)
        showBackButton(backButton)
    }

    protected fun showToolbar(title: Int, backButton: Boolean = false) {
        setupToolbar(getString(title))
        showBackButton(backButton)
    }


    private fun setupToolbar(title: String) {
        if (activity is IToolbar) {
            val toolbar = activity as IToolbar
            toolbar.setToolbarTitle(title)
        }
    }

    private fun showBackButton(showBackButton: Boolean) {
        (activity as BaseAct).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(showBackButton)
            if (showBackButton) {
                //TODO add back button
            }
        }
    }
}