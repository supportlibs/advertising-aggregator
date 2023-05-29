package com.lib.advertising_control.remote_config

import android.content.Context
import com.applovin.sdk.AppLovinSdk
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

public class InitializationManager {

    fun initAd(context: Context, list: List<String>) {
        MobileAds.initialize(context) {
            it.adapterStatusMap.forEach {
            }
        }
        RequestConfiguration.Builder().setTestDeviceIds(list)

        AppLovinSdk.getInstance(context).apply {
            mediationProvider = "max"
            initializeSdk()
        }
    }
}