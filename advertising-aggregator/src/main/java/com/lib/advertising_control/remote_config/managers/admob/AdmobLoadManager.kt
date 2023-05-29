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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AdmobLoadManager(
    private val context: Context,
    private val isTestAd: Boolean
) : InterstitialLoadManager() {

    override suspend fun loadInterstitial(
        adModel: ConfigAdModel,
        scope: CoroutineScope
    ): BaseAdObject {
        val flow = MutableSharedFlow<BaseAdObject>()
        val callback = object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                if (scope.isActive) scope.launch {
                    flow.emit(BaseAdObject.Error("Admob ad failed to load"))
                }
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                if (scope.isActive) scope.launch {
                    flow.emit(BaseAdObject.AdmobAdObject(interstitialAd))
                }
            }
        }

        AdManagerInterstitialAd.load(
            context,
            getAdmobProdIdOrTestId(adModel),
            AdRequest.Builder().setHttpTimeoutMillis(29000).build(),
            callback
        )

        return flow.first()
    }

    private fun getAdmobProdIdOrTestId(configAdModel: ConfigAdModel): String {
        return if (configAdModel.isTest || BuildConfig.DEBUG || isTestAd)
            BuildConfig.ADMOB_TEST_INTERSTTIAL
        else
            configAdModel.id
    }

}