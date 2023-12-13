package com.lib.advertising_control.remote_config.managers.applovin

import android.app.Activity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
import com.lib.advertising_control.remote_config.managers.base.InterstitialLoadManager
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ApplovinLoadManager(
    private val activity: Activity
) : InterstitialLoadManager() {

    override suspend fun loadInterstitial(adModel: ConfigAdModel, scope: CoroutineScope): BaseAdObject {
        val flow = MutableSharedFlow<BaseAdObject>()
        val interstitialAd = MaxInterstitialAd(adModel.id.ifEmpty { "empty" }, activity)

        val callback = object : MaxAdListener {
            override fun onAdLoaded(p0: MaxAd) {
                if (scope.isActive) scope.launch {
                    flow.emit(BaseAdObject.ApplovinAdObject(interstitialAd))
                }
            }

            override fun onAdLoadFailed(p0: String, p1: MaxError) {
                if (scope.isActive) scope.launch {
                    flow.emit(BaseAdObject.Error("Applovin ad failed to load"))
                }
            }

            override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
                if (scope.isActive) scope.launch {
                    flow.emit(BaseAdObject.Error("Applovin ad display failed"))
                }
            }

            override fun onAdDisplayed(p0: MaxAd) {}
            override fun onAdHidden(p0: MaxAd) {}
            override fun onAdClicked(p0: MaxAd) {}
        }

        interstitialAd.setListener(callback)
        interstitialAd.loadAd()

        return flow.first()
    }

}