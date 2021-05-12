package com.paninti.lib.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paninti.lib.base.viewmodel.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import kotlin.coroutines.CoroutineContext

abstract class BaseAct: AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    fun showMessage(message: Message) {
        when (message) {
            is Message.Toast -> showMessageToast(message.message)
            is Message.Dialog -> showMessageDialog(message.message)
        }
    }

    fun showMessageDialog(msg: String?) {
        msg?.let {
            alert {
                message = msg
                okButton { d -> d.dismiss() }
            }.show()
        }
    }

    fun showMessageToast(msg: String?) {
        msg?.let { toast(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}