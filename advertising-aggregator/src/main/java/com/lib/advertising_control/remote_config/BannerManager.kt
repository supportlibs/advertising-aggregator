package com.lib.advertising_control.remote_config

import android.app.Activity
import android.view.ViewGroup
import com.lib.advertising_control.remote_config.managers.admob.AdmobBannerManager
import com.lib.advertising_control.remote_config.managers.applovin.ApplovinBannerManager
import com.lib.advertising_control.remote_config.managers.base.BaseBannerManager
import com.lib.advertising_control.remote_config.managers.facebook.FacebookBannerManager
import com.lib.advertising_control.remote_config.managers.none.NoneBannerManager
import com.lib.advertising_control.remote_config.model.AdType
import com.lib.advertising_control.remote_config.model.BannerState
import com.lib.advertising_control.remote_config.model.RemoteConfigFetchStatus.FETCH_COMPLETED
import com.lib.advertising_control.remote_config.model.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect

public class BannerManager(
    private val activity: Activity,
    private val scope: CoroutineScope,
    private val bannerContainer: ViewGroup
) {

    private val remoteConfigManager: AdRemoteConfigManager = AdRemoteConfigManager()
    private var bannerProviderManager: BaseBannerManager = NoneBannerManager()

    suspend fun initBanner(adBlock: String) {
        remoteConfigManager.loadInfoFromRemoteConfig().collect { remoteConfigLoadStatus ->
            if (remoteConfigLoadStatus == FETCH_COMPLETED) {
                when (val config = remoteConfigManager.getAdRequest(adBlock)) {
                    is RequestState.Error -> { }
                    is RequestState.Success -> {
                        for (adModel in config.data.sortedBy { it.priority }) {
                            when (adModel.adsProvider) {
                                AdType.ADMOB -> initAdmob()
                                AdType.FACEBOOK -> initFacebook()
                                AdType.APPLOVIN -> initApplovin()
                                AdType.NONE -> {}
                            }
                            if (bannerProviderManager.initBanner(adModel) == BannerState.LOADED) {
                                break
                            } else {
                                continue
                            }
                        }
                    }
                }
            }
        }
    }

    fun pauseBanner() = bannerProviderManager.pauseBanner()

    fun resumeBanner() = bannerProviderManager.resumeBanner()

    fun destroyBanner() = bannerProviderManager.destroyBanner()

    private fun initAdmob() {
        bannerProviderManager = AdmobBannerManager(activity, bannerContainer, scope)
    }

    private fun initFacebook() {
        bannerProviderManager = FacebookBannerManager(activity, bannerContainer, scope)
    }

    private fun initApplovin() {
        bannerProviderManager = ApplovinBannerManager(activity, bannerContainer, scope)
    }
}