package com.lib.advertising_control.remote_config.managers.applovin

import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.lib.advertising_control.remote_config.managers.base.InterstitialShowManager
import com.lib.advertising_control.remote_config.model.InterstitialShowState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class ApplovinShowManager(
    private val applovinAd: MaxInterstitialAd
) : InterstitialShowManager {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun showInterstitial() = callbackFlow {
        applovinAd.setListener(object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
            }

            override fun onAdDisplayed(p0: MaxAd?) {
                trySend(InterstitialShowState.SHOWED)
            }

            override fun onAdHidden(p0: MaxAd?) {
                trySend(InterstitialShowState.DISMISSED)
            }

            override fun onAdClicked(p0: MaxAd?) {
            }

            override fun onAdLoadFailed(p0: String?, p1: MaxError?) {
            }

            override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError?) {
                trySend(InterstitialShowState.FAILED_TO_SHOW)
            }
        })
        applovinAd.showAd()
        awaitClose {  }
    }

}