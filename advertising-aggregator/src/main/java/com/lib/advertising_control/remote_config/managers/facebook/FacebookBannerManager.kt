package com.lib.advertising_control.remote_config.managers.facebook

import android.content.Context
import android.view.ViewGroup
import com.lib.advertising_control.remote_config.managers.base.BaseBannerManager
import com.lib.advertising_control.remote_config.model.BannerState
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first

class FacebookBannerManager(
    private val context: Context,
    private val bannerContainer: ViewGroup,
    private val scope: CoroutineScope
) : BaseBannerManager() {
//
//    private val TAG = this.javaClass.simpleName
//
//    var adView: com.facebook.ads.AdView? = null

    override suspend fun initBanner(adModel: ConfigAdModel): BannerState {
//        adView = com.facebook.ads.AdView(
//            context,
//            adModel.id,
//            com.facebook.ads.AdSize.BANNER_HEIGHT_50
//        )
//
//        adView?.loadAd(
//            adView
//                ?.buildLoadAdConfig()
//                ?.withAdListener(object : com.facebook.ads.AdListener {
//                    override fun onError(p0: Ad?, p1: AdError?) {
//                        if (scope.isActive) scope.launch {
//                            bannerStateFlow.emit(BannerState.FAILED)
//                        }
//                    }
//
//                    override fun onAdLoaded(p0: Ad?) {
//                        Log.d(TAG, "onAdLoaded: ")
//                        if (scope.isActive) scope.launch {
//                            bannerStateFlow.emit(BannerState.LOADED)
//                        }
//                        bannerContainer.addView(adView)
//                    }
//
//                    override fun onAdClicked(p0: Ad?) {
//                        Log.d(TAG, "onAdClicked: ")
//                    }
//
//                    override fun onLoggingImpression(p0: Ad?) {
//                        Log.d(TAG, "onLoggingImpression: ")
//                    }
//                })
//                ?.build()
//        )
        return bannerStateFlow.first()
    }

    override fun pauseBanner() {}

    override fun resumeBanner() {}

    override fun destroyBanner() {
//        adView?.destroy()
    }


}

