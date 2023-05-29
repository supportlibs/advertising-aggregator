package com.lib.advertising_control.remote_config.managers.base

import com.lib.advertising_control.remote_config.model.BannerState
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseBannerManager {

    val bannerStateFlow = MutableSharedFlow<BannerState>()

    abstract suspend fun initBanner(adModel: ConfigAdModel): BannerState

    abstract fun pauseBanner()

    abstract fun resumeBanner()

    abstract fun destroyBanner()

}