package com.lib.advertising_control.remote_config.managers.admob

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.lib.advertising_control.BuildConfig
import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
import com.lib.advertising_control.remote_config.managers.base.InterstitialLoadManager
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class AdmobLoadManager(
    private val context: Context,
    private val isTestAd: Boolean
) : InterstitialLoadManager() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadInterstitial(
        adModel: ConfigAdModel
    ) = callbackFlow {

        val callback = object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                trySend(BaseAdObject.Error("${adError.code} : ${adError.message}"))
                channel.close()
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                trySend(BaseAdObject.AdmobAdObject(interstitialAd))
                channel.close()
            }
        }

        AdManagerInterstitialAd.load(
            context,
            getAdmobProdIdOrTestId(adModel),
            AdRequest.Builder().setHttpTimeoutMillis(16000).build(),
            callback
        )
        awaitClose { }
    }

    private fun getAdmobProdIdOrTestId(configAdModel: ConfigAdModel): String {
        return if (configAdModel.isTest || BuildConfig.DEBUG || isTestAd)
            BuildConfig.ADMOB_TEST_INTERSTTIAL
        else
            configAdModel.id
    }
}