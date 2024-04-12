package com.ziguhonglan.plugins.gromore.ad.reward

import android.app.Activity
import android.os.Bundle
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTRewardVideoAd
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot



object ZGGromoreReward {

    //构造激励视频广告的Adlsot
    fun buildRewardAdslot(slotId: String): AdSlot {
        return AdSlot.Builder()
            .setCodeId(slotId)  //广告位ID
            .setOrientation(TTAdConstant.VERTICAL)  //激励视频方向
            .setMediationAdSlot(
                MediationAdSlot.Builder()
                    .setRewardName("体力")
                    .setRewardAmount(10)
//                    .setExtraObject("", "")
                    .build()
            )
            .build()
    }



    //加载激励视频
    fun loadRewardAd(act: Activity, slotId: String) {
        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(act)
        //这里为激励视频的简单功能，如需使用复杂功能，如gromore的服务端奖励验证，请参考demo中的AdUtils.kt类中激励部分
        adNativeLoader.loadRewardVideoAd(
            buildRewardAdslot(slotId = slotId),
            object : TTAdNative.RewardVideoAdListener {
                override fun onError(errorCode: Int, errorMsg: String?) {
                    //广告加载失败
                }

                override fun onRewardVideoAdLoad(ttRewardVideoAd: TTRewardVideoAd?) {
                    //广告加载失败
                }

                override fun onRewardVideoCached() {
                    //广告缓存成功 此api已经废弃，请使用onRewardVideoCached(ttRewardVideoAd: TTRewardVideoAd?)
                }

                override fun onRewardVideoCached(ttRewardVideoAd: TTRewardVideoAd?) {
                    //广告缓存成功 在此回调中进行广告展示
                    showRewardAD(act, ttRewardVideoAd)
                }

            })
    }



    //展示激励视频
    fun showRewardAD(act: Activity, ttRewardVideoAd: TTRewardVideoAd?) {
        if (act == null) {
            return
        }
        ttRewardVideoAd?.let {
            if (it.mediationManager.isReady) {
                it.setRewardAdInteractionListener(object :
                    TTRewardVideoAd.RewardAdInteractionListener {
                    override fun onAdShow() {
                        //广告展示
                        //获取展示广告相关信息，需要再show回调之后进行获取
                        var manager = it.mediationManager;
                        if (manager != null && manager.showEcpm != null) {
                            val ecpm = manager.showEcpm.ecpm //展示广告的价格
                            val sdkName = manager.showEcpm.sdkName  //展示广告的adn名称
                            val slotId = manager.showEcpm.slotId //展示广告的代码位ID
                        }
                    }

                    override fun onAdVideoBarClick() {
                        //广告点击
                    }

                    override fun onAdClose() {
                        //广告关闭
                    }

                    override fun onVideoComplete() {
                        //广告视频播放完成
                    }

                    override fun onVideoError() {
                        //广告视频错误
                    }

                    override fun onRewardVerify(
                        rewardVerify: Boolean,
                        rewardAmount: Int,
                        rewardName: String?,
                        errorCode: Int,
                        errorMsg: String?
                    ) {
                        //奖励发放 已废弃 请使用 onRewardArrived 替代
                    }

                    override fun onRewardArrived(
                        isRewardValid: Boolean,
                        rewardType: Int,
                        extraInfo: Bundle?
                    ) {
                        //奖励发放
                        if (isRewardValid) {
                            // 验证通过
                            // 从extraInfo读取奖励信息
                        } else {
                            // 未验证通过
                        }
                    }

                    override fun onSkippedVideo() {
                        //广告跳过
                    }
                })
                it.showRewardVideoAd(act) //展示插全屏广告
            } else {
                //RewardVideo is not ready
            }
        }
    }

}