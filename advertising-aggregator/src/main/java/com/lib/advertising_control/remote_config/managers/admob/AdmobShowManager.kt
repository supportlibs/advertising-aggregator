package com.lib.advertising_control.remote_config.managers.admob

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.lib.advertising_control.remote_config.managers.base.InterstitialShowManager
import com.lib.advertising_control.remote_config.model.InterstitialShowState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AdmobShowManager(
    private val activity: Activity,
    private val ad: InterstitialAd?
) : InterstitialShowManager {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun showInterstitial() = callbackFlow {
        Log.d(this::class.java.simpleName, "showInterstitial() has been invoked.")

        ad?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(this.javaClass.simpleName, "Interstitial AD Dismissed...")
                trySend(InterstitialShowState.DISMISSED)
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(this.javaClass.simpleName, "Interstitial AD Showed...")
                trySend(InterstitialShowState.SHOWED)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Log.d(this.javaClass.simpleName, "Interstitial AD Failed to show. Error: ${adError.message}")
                trySend(InterstitialShowState.FAILED_TO_SHOW)
            }
        }
        ad?.show(activity)
        awaitClose {  }
    }

}