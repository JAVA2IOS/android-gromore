package com.ziguhonglan.plugins.gromore

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ziguhonglan.plugins.center.databinding.NormalAppActivityBinding

class NormalAppActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(NormalAppActivityBinding.inflate(layoutInflater).root)
    }
}