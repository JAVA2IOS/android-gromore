package com.ziguhonglan.plugins.gromore

import android.app.Application
import com.ziguhonglan.plugins.gromore.init.ZGGromorePlguin


const val APPID = "xxx"
const val SlotID = "xxx"
class PGromoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()


        // 初始化SDK
        ZGGromorePlguin.initAdsSdk(context = this) {
            appID = APPID
            appName = "name"
        }
    }
}