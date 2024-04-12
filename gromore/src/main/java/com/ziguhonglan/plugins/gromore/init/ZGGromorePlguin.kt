package com.ziguhonglan.plugins.gromore.init

import android.content.Context
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTCustomController
import com.bytedance.sdk.openadsdk.mediation.init.MediationPrivacyConfig

object ZGGromorePlguin {

    var isInitialSuccess: Boolean = false
        private set

    //初始化聚合sdk
    fun initAdsSdk(context: Context) {
        if (isInitialSuccess)
            return

        if (TTAdSdk.isSdkReady())
            return

        TTAdSdk.init(context, buildConfig(context))
        TTAdSdk.start(object : TTAdSdk.Callback {
            override fun success() {
                //初始化成功
                //在初始化成功回调之后进行广告加载
                isInitialSuccess = true
            }

            override fun fail(code: Int, msg: String?) {
                //初始化失败
            }
        })
    }

    //初始化聚合sdk
    fun initAdsSdk(context: Context, build: ZGGromoreDefine.Builder.() -> Unit) {

        if (isInitialSuccess)
            return

        // 参数配置
        ZGGromoreDefine.build(build)

        TTAdSdk.init(context, buildConfig(context))
        TTAdSdk.start(object : TTAdSdk.Callback {
            override fun success() {
                //初始化成功
                //在初始化成功回调之后进行广告加载
                isInitialSuccess = true
            }

            override fun fail(code: Int, msg: String?) {
                //初始化失败
            }
        })
    }


    // 构造TTAdConfig
    private fun buildConfig(context: Context): TTAdConfig {
        return TTAdConfig.Builder()
            .appId(ZGGromoreDefine.appID) //APP ID
            .appName(ZGGromoreDefine.appName) //APP Name
            .useMediation(true)  //开启聚合功能
            .debug(ZGGromoreDefine.debuggable)  //打开debug开关
            /**
             * 多进程增加注释说明：V>=5.1.6.0支持多进程，如需开启可在初始化时设置.supportMultiProcess(true) ，默认false；
             * 注意：开启多进程开关时需要将ADN的多进程也开启，否则广告展示异常，影响收益。
             * CSJ、gdt无需额外设置，KS、baidu、Sigmob、Mintegral需要在清单文件中配置各家ADN激励全屏xxxActivity属性android:multiprocess="true"
             */
            .supportMultiProcess(ZGGromoreDefine.multiProcess)  //支持多进程
            .customController(getTTCustomController())  //设置隐私权
            .build()
    }

    //设置隐私合规
    private fun getTTCustomController(): TTCustomController? {
        return object : TTCustomController() {

            override fun getMediationPrivacyConfig(): MediationPrivacyConfig? {
                return object : MediationPrivacyConfig() {
                    override fun isLimitPersonalAds(): Boolean {  //是否限制个性化广告
                        return ZGGromoreDefine.limitedPersonalAds
                    }
                }
            }

            override fun getAndroidId(): String? {
                return ZGGromoreDefine.androidID ?: super.getAndroidId()
            }
        }
    }

}