package com.paninti.lib.network.model

/**
 * Proguard info, don't inherit when its only contain object. Inherit when its contain List
 * */
open class BaseDataModel<out DATA : Any>(
    val alert: Alerts = Alerts(),
    val data: DATA? = null
)

data class Alerts(
    val code: Int = -1,
    val message: String = ""
)