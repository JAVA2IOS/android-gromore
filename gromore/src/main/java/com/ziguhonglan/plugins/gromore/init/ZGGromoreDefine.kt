package com.ziguhonglan.plugins.gromore.init

import com.ziguhonglan.plugins.gromore.BuildConfig

object ZGGromoreDefine {

    var appID: String = ""
        private set

    var appName: String = ""
        private set

    var debuggable: Boolean = BuildConfig.DEBUG
        private set

    /**
     * 多进程
     * default false
     */
    var multiProcess: Boolean = false
        private set

    /**
     * 是否限制个性广告推荐
     * default false
     */
    var limitedPersonalAds: Boolean = false
        private set

    /**
     * Android ID
     */
    var androidID: String? = null
        private set

    class Builder internal constructor(
        var appID: String = "",
        var appName: String = "",
        var debuggable: Boolean = false,
        var multiProcess: Boolean = false,
        var limitedPersonalAds: Boolean = false,
        var androidID: String? = null
    ) {

        fun  build() {
            ZGGromoreDefine.let {
                it.appID = appID
                it.appName = appName
                it.debuggable = debuggable
                it.multiProcess = multiProcess
                it.limitedPersonalAds = limitedPersonalAds
                it.androidID = androidID
            }
        }
    }

    fun build(b1: Builder.() -> Unit) {
        Builder(
            appID = appID,
            appName = appName,
            debuggable = debuggable,
            multiProcess = multiProcess,
            limitedPersonalAds = limitedPersonalAds,
            androidID = androidID
        ).apply(b1).build()
    }
}