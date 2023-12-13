package com.lib.advertising_control.remote_config.managers.applovin

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils
import com.lib.advertising_control.remote_config.managers.base.BaseBannerManager
import com.lib.advertising_control.remote_config.model.BannerState
import com.lib.advertising_control.remote_config.model.BannerState.FAILED
import com.lib.advertising_control.remote_config.model.BannerState.LOADED
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class ApplovinBannerManager(
    private val activity: Activity,
    private val bannerContainer: ViewGroup,
    private val scope: CoroutineScope
) : BaseBannerManager() {

    private val TAG = this::class.java.simpleName

    private lateinit var adView: MaxAdView

    override suspend fun initBanner(adModel: ConfigAdModel): BannerState {
        
        adView = MaxAdView(adModel.id.ifEmpty { "empty" }, activity)
        adView.setListener(ApplovinAdListener())

        val width = ViewGroup.LayoutParams.MATCH_PARENT

        val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).height
        val heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp)

        adView.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        adView.setExtraParameter("adaptive_banner", "true")

        adView.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.darker_gray))

        adView.loadAd()

        return bannerStateFlow.first()
    }

    inner class ApplovinAdListener : MaxAdViewAdListener {
        override fun onAdLoaded(p0: MaxAd) {
            Log.d(TAG, "onAdLoaded: ")
            bannerContainer.apply {
                if (childCount == 0) addView(adView)
            }
            if (scope.isActive) scope.launch {
                bannerStateFlow.emit(LOADED)
            }
        }

        override fun onAdDisplayed(p0: MaxAd) {
            Log.d(TAG, "onAdDisplayed: ")
        }

        override fun onAdHidden(p0: MaxAd) {
            Log.d(TAG, "onAdHidden: ")
        }

        override fun onAdClicked(p0: MaxAd) {
            Log.d(TAG, "onAdClicked: ")
        }

        override fun onAdLoadFailed(p0: String, p1: MaxError) {
            Log.d(TAG, "onAdLoadFailed: ${p1?.message}")
            if (scope.isActive) scope.launch {
                bannerStateFlow.emit(FAILED)
            }
        }

        override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
            Log.d(TAG, "onAdDisplayFailed: ${p1?.message}")
            if (scope.isActive) scope.launch {
                bannerStateFlow.emit(FAILED)
            }
        }

        override fun onAdExpanded(p0: MaxAd) {}

        override fun onAdCollapsed(p0: MaxAd) {}
    }

    override fun pauseBanner() {}

    override fun resumeBanner() {}

    override fun destroyBanner() = adView.destroy()

}