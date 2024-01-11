package com.lib.advertising_control.remote_config.managers.facebook
//
//import android.content.Context
//import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
//import com.lib.advertising_control.remote_config.managers.base.InterstitialLoadManager
//import com.lib.advertising_control.remote_config.model.ConfigAdModel
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.first
//
//class FacebookLoadManager(
//    private val context: Context
//) : InterstitialLoadManager() {
//
//    override suspend fun loadInterstitial(adModel: ConfigAdModel, scope: CoroutineScope): BaseAdObject {
//        val flow = MutableSharedFlow<BaseAdObject>()
//        val facebookInterstitialAd = InterstitialAd(context, adModel.id)
//
//        val callback = object : InterstitialAdListener {
//            override fun onError(p0: Ad?, p1: AdError?) {
//                if (scope.isActive) scope.launch {
//                    flow.emit(BaseAdObject.Error("Facebook ad failed to load"))
//                }
//            }
//
//            override fun onAdLoaded(ad: Ad?) {
//                if (scope.isActive) scope.launch {
//                    flow.emit(BaseAdObject.FacebookAdObject(facebookInterstitialAd))
//                }
//            }
//
//            override fun onAdClicked(p0: Ad?) {}
//            override fun onLoggingImpression(p0: Ad?) {}
//            override fun onInterstitialDisplayed(p0: Ad?) {}
//            override fun onInterstitialDismissed(p0: Ad?) {}
//        }
//
//        facebookInterstitialAd.loadAd(
//            facebookInterstitialAd.buildLoadAdConfig()
//                .withAdListener(callback)
//                .build()
//        )
//
//        return flow.first()
//    }
//
//}