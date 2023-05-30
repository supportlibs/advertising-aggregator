package com.lib.advertising_control.remote_config

import android.content.Context
import com.applovin.sdk.AppLovinSdk
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

public class InitializationManager {

    fun initAdmob(context: Context, list: List<String>) {
        MobileAds.initialize(context) {
            it.adapterStatusMap.forEach {
            }
        }
        RequestConfiguration.Builder().setTestDeviceIds(list)

    }

    fun initApplovin(context: Context) {
        AppLovinSdk.getInstance(context).apply {
            mediationProvider = "max"
            initializeSdk()
        }
    }
}