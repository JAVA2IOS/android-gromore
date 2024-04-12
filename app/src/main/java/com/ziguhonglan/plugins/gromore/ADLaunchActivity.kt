package com.ziguhonglan.plugins.gromore

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.ziguhonglan.plugins.center.databinding.AdSplashActivityBinding
import com.ziguhonglan.plugins.gromore.ad.splash.ZGGromoreSplash
import com.ziguhonglan.plugins.gromore.ad.splash.ZGGromoreSplashAdState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ADLaunchActivity: FragmentActivity() {

    private lateinit var splashAdView: ConstraintLayout

    private val TAG = "ADLaunchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdSplashActivityBinding.inflate(layoutInflater).apply {
            splashAdView = adSplashView
            setContentView(root)
        }

        //开屏广告
        lifecycleScope.launch {
            delay(2000)
            ZGGromoreSplash.showSplash(
                context = this@ADLaunchActivity,
                adSlotId = SlotID,
                splashContainer = splashAdView,
            )
                .collectLatest {
                    Log.d(TAG, "collectLatest: 当前状态: $it")
                    when (it) {
                        is ZGGromoreSplashAdState.Displaying -> {
                            Log.d(TAG, "onCreate: displaying: $it")
                        }

                        is ZGGromoreSplashAdState.LoadFailure,
                        is ZGGromoreSplashAdState.RenderFailure,
                        ZGGromoreSplashAdState.Closed -> {
                            startActivity(Intent().apply {
                                setClass(this@ADLaunchActivity, NormalAppActivity::class.java)
                            })
                            finish()
                        }

                        else -> {}
                    }
                }
        }
    }
}