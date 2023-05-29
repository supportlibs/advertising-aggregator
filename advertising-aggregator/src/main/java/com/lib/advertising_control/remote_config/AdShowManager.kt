package com.lib.advertising_control.remote_config

import android.app.Activity
import com.lib.advertising_control.remote_config.managers.admob.AdmobShowManager
import com.lib.advertising_control.remote_config.managers.applovin.ApplovinShowManager
import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
import com.lib.advertising_control.remote_config.managers.base.BaseAdObject.AdmobAdObject
import com.lib.advertising_control.remote_config.managers.base.BaseAdObject.ApplovinAdObject
import com.lib.advertising_control.remote_config.managers.base.InterstitialShowManager
import com.lib.advertising_control.remote_config.managers.none.NoneShowManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

public class AdShowManager(private val activity: Activity) {

    private var interstitialShowManager: InterstitialShowManager = NoneShowManager()

    suspend fun showAd(adObject: BaseAdObject)= flow {
        interstitialShowManager = when (adObject) {
            is AdmobAdObject -> AdmobShowManager(activity, adObject.ad)
            is ApplovinAdObject -> ApplovinShowManager(adObject.ad)
            else -> NoneShowManager()
        }
        emit(interstitialShowManager.showInterstitial().first())
    }

}