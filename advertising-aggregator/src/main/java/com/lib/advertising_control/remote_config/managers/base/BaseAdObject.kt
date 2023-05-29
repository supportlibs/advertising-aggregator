package com.lib.advertising_control.remote_config.managers.base

import com.applovin.mediation.ads.MaxInterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAd as AdmobAd

sealed class BaseAdObject{
    data class AdmobAdObject(val ad: AdmobAd) : BaseAdObject()
//    data class FacebookAdObject(val ad: FacebookAd) : BaseAdObject()
    data class ApplovinAdObject(val ad: MaxInterstitialAd) : BaseAdObject()
    object NoneAdObject : BaseAdObject()
    data class Error(val e: String) : BaseAdObject()
}


