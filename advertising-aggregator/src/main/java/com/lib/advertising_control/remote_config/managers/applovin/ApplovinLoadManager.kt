package com.lib.advertising_control.remote_config.managers.applovin

import android.app.Activity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
import com.lib.advertising_control.remote_config.managers.base.InterstitialLoadManager
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow

class ApplovinLoadManager(
    private val activity: Activity
) : InterstitialLoadManager() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadInterstitial(adModel: ConfigAdModel) = callbackFlow {

        val interstitialAd = MaxInterstitialAd(adModel.id.ifEmpty { "empty" }, activity)

        interstitialAd.setListener(object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                trySend(BaseAdObject.ApplovinAdObject(interstitialAd))
                channel.close()
            }

            override fun onAdLoadFailed(p0: String, maxError: MaxError) {
                trySend(BaseAdObject.Error("${maxError.code} : ${maxError.message}"))
                channel.close()
            }

            override fun onAdDisplayFailed(p0: MaxAd, maxError: MaxError) { }
            override fun onAdDisplayed(p0: MaxAd) { }
            override fun onAdHidden(p0: MaxAd) { }
            override fun onAdClicked(p0: MaxAd) { }
        })

        interstitialAd.loadAd()

        delay(16 * 1000)
        trySend(BaseAdObject.Error("Timeout while loading Applovin ad"))
        channel.close()
        interstitialAd.destroy()
        awaitClose { }
    }

}