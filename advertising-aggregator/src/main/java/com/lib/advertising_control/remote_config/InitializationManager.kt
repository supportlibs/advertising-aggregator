package com.lib.advertising_control.remote_config

import android.content.Context
import com.applovin.sdk.AppLovinSdk
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration

public class InitializationManager {

    fun initAdmob(context: Context, list: List<String> = listOf()) {
        MobileAds.initialize(context) {
            it.adapterStatusMap.forEach {
            }
        }
        RequestConfiguration.Builder().setTestDeviceIds(list)

    }

    fun initApplovin(context: Context, list: List<String> = listOf()) {
        AppLovinSdk.getInstance(context).apply {
            mediationProvider = "max"
            settings.testDeviceAdvertisingIds = list
            initializeSdk()
        }
    }
}