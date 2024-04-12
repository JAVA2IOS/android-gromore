package com.ziguhonglan.plugins.gromore.ad.splash

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.CSJAdError
import com.bytedance.sdk.openadsdk.CSJSplashAd
import com.bytedance.sdk.openadsdk.TTAdInteractionListener
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.ziguhonglan.plugins.gromore.model.ZGGromoreAd
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest

sealed interface ZGGromoreSplashAdState {
    /**
     * 正在加载
     */
    data object Loading: ZGGromoreSplashAdState

    /**
     * 加载成功
     */
    data object LoadSuccess: ZGGromoreSplashAdState

    /**
     * 加载失败
     */
    data class LoadFailure(
        val error: Error?
    ): ZGGromoreSplashAdState

    /**
     * 准备展示
     */
    data object Ready: ZGGromoreSplashAdState

    /**
     * 正在展示
     */
    data class Displaying(
        val ad: ZGGromoreAd
    ): ZGGromoreSplashAdState

    /**
     * 展示失败
     */
    data class RenderFailure(
        val error: Error?
    ): ZGGromoreSplashAdState

    /**
     * 广告关闭
     */
    data object Closed: ZGGromoreSplashAdState

    /**
     * 广告点击
     */
    data object Clicked: ZGGromoreSplashAdState
}

object ZGGromoreSplash {

    fun showSplash(context: Activity, adSlotId: String, splashContainer: ViewGroup, timeoutMillis: Int = 5000) = callbackFlow{

        if (TTAdSdk.isSdkReady().not()) {
            trySendBlocking(ZGGromoreSplashAdState.LoadFailure(Error("sdk未初始化")))
            awaitClose()
            return@callbackFlow
        }

        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(context)

        trySendBlocking(ZGGromoreSplashAdState.Loading)

        var splashAd: CSJSplashAd? = null

        adNativeLoader.loadSplashAd(buildSplashAdslot(adSlotId = adSlotId), object : TTAdNative.CSJSplashAdListener {

            override fun onSplashLoadSuccess(p0: CSJSplashAd?) {
                splashAd = p0
                trySendBlocking(ZGGromoreSplashAdState.LoadSuccess)
            }

            override fun onSplashLoadFail(p1: CSJAdError?) {
                //广告加载失败
                trySendBlocking(ZGGromoreSplashAdState.LoadFailure(Error("code: ${p1?.code} msg: ${p1?.msg}")))
                close()
            }

            override fun onSplashRenderSuccess(csjSplashAd: CSJSplashAd?) {
                //广告渲染成功，在此展示广告
                showSplashAd(csjSplashAd, splashContainer)
            }

            override fun onSplashRenderFail(p0: CSJSplashAd?, p1: CSJAdError?) {
                //广告渲染失败
                trySendBlocking(ZGGromoreSplashAdState.RenderFailure(Error("code: ${p1?.code} msg: ${p1?.msg}")))
                close()
            }
        }, timeoutMillis)

        awaitClose {
            splashAd?.mediationManager?.destroy()
        }
    }

    //构造开屏广告的Adslot
    private fun buildSplashAdslot(adSlotId: String): AdSlot {
        return AdSlot.Builder()
            .setCodeId(adSlotId)
            .setImageAcceptedSize(750,1600)  //设置广告宽高 单位px
            .build()
    }

    //展示开屏广告
    fun ProducerScope<ZGGromoreSplashAdState>.showSplashAd(ad: CSJSplashAd?, container: ViewGroup?) {
        ad?.let {

            it.setSplashAdListener(object : CSJSplashAd.SplashAdListener {
                override fun onSplashAdShow(csjSplashAd: CSJSplashAd?) {
                    //广告展示
                    //获取展示广告相关信息，需要再show回调之后进行获取
                    var manager = it.mediationManager;
                    if (manager != null && manager.showEcpm != null) {
                        val ecpm = manager.showEcpm.ecpm //展示广告的价格
                        val sdkName = manager.showEcpm.sdkName  //展示广告的adn名称
                        val slotId = manager.showEcpm.slotId //展示广告的代码位ID
                        Log.d("ZGGromoreSplash", "onSplashAdClose: 当前广告： $sdkName")
                        trySendBlocking(ZGGromoreSplashAdState.Displaying(
                            ad = ZGGromoreAd.Splash(
                                slot = slotId,
                                adn = sdkName,
                                price = ecpm
                            )
                        ))
                    }
                }

                override fun onSplashAdClick(csjSplashAd: CSJSplashAd?) {
                    //广告点击
                    trySendBlocking(ZGGromoreSplashAdState.Clicked)
                }

                override fun onSplashAdClose(csjSplashAd: CSJSplashAd?, p1: Int) {

                    Log.d("ZGGromoreSplash", "onSplashAdClose: 当前信息: $p1")
                    when (p1) {
                        2 -> {
                            // 倒计时 广告关闭
                            trySendBlocking(ZGGromoreSplashAdState.Closed)
                            close()
                        }
                        else -> {}
                    }
                }

            })
            if (container != null) {
                trySendBlocking(ZGGromoreSplashAdState.Ready)
                it.showSplashView(container) //展示开屏广告
            } else {
                trySendBlocking(ZGGromoreSplashAdState.RenderFailure(Error("无法展开广告")))
                close()
            }
        }
            ?: run {
                trySendBlocking(ZGGromoreSplashAdState.RenderFailure(Error("广告不存在")))
                close()
            }
    }
}