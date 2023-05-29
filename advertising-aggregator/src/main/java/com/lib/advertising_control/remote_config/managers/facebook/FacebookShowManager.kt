package com.lib.advertising_control.remote_config.managers.facebook

import com.lib.advertising_control.remote_config.managers.base.InterstitialShowManager
import com.lib.advertising_control.remote_config.model.InterstitialShowState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FacebookShowManager(
//    private val facebookAd : InterstitialAd
) : InterstitialShowManager {

    override fun showInterstitial(): Flow<InterstitialShowState> {
//        Log.d(this::class.java.simpleName, "showInterstitial() has been invoked.")
//
//        facebookAd.show()
        return flowOf()
    }

}