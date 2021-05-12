package com.paninti.lib.base.viewmodel

sealed class Message {
    class Toast(val message: String?) : Message()
    class Dialog(val message: String?) : Message()
}