package com.lib.advertising_control.remote_config.managers.admob

import android.app.Activity
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.lib.advertising_control.BuildConfig
import com.lib.advertising_control.remote_config.managers.base.BaseBannerManager
import com.lib.advertising_control.remote_config.model.BannerState
import com.lib.advertising_control.remote_config.model.BannerState.FAILED
import com.lib.advertising_control.remote_config.model.BannerState.LOADED
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AdmobBannerManager(
    private val activity: Activity,
    private val containerView: ViewGroup,
    private val scope: CoroutineScope
) : BaseBannerManager() {

    private val TAG = this.javaClass.simpleName

    lateinit var adView: AdView

    override suspend fun initBanner(adModel: ConfigAdModel): BannerState {

        adView = AdView(activity)
        adView.setAdSize(adaptiveAdSize)

        adView.adUnitId = getAdmobProdIdOrTestId(adModel)
        adView.adListener = AdmobBannerListener()

        containerView.addView(adView)

        val adRequest = AdRequest.Builder()
            .setHttpTimeoutMillis(29000)
            .build()

        adView.loadAd(adRequest)

        return bannerStateFlow.first()
    }

    //TODO: check crashlytics later
    private val adaptiveAdSize: AdSize
        get() {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.getDefaultDisplay().getMetrics(displayMetrics)

            val density = displayMetrics.density

            var adWidthPixels = containerView.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = displayMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
        }

    inner class AdmobBannerListener : AdListener() {
        override fun onAdLoaded() {
            super.onAdLoaded()
            Log.d(TAG, "The banner was successfully loaded.")
            adView.visibility = View.VISIBLE
            if (scope.isActive) scope.launch {
                bannerStateFlow.emit(LOADED)
            }
        }

        override fun onAdFailedToLoad(adError: LoadAdError) {
            super.onAdFailedToLoad(adError)
            Log.e(TAG, "Failed to load banner. ${adError.message}")
            adView.visibility = View.GONE
            if (scope.isActive) scope.launch {
                bannerStateFlow.emit(FAILED)
            }
        }
    }

    private fun getAdmobProdIdOrTestId(configAdModel: ConfigAdModel): String {
        return if (configAdModel.isTest || BuildConfig.BUILD_TYPE.contains("debug", true))
            BuildConfig.ADMOB_TEST_BANNER
        else
            configAdModel.id
    }

    override fun pauseBanner() = adView.pause()

    override fun resumeBanner() = adView.resume()

    override fun destroyBanner() = adView.destroy()

}