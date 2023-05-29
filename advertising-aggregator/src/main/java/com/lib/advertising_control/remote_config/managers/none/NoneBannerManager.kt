package com.lib.advertising_control.remote_config.managers.none

import com.lib.advertising_control.remote_config.managers.base.BaseBannerManager
import com.lib.advertising_control.remote_config.model.BannerState
import com.lib.advertising_control.remote_config.model.BannerState.LOADED
import com.lib.advertising_control.remote_config.model.ConfigAdModel

class NoneBannerManager : BaseBannerManager() {

    override suspend fun initBanner(adModel: ConfigAdModel): BannerState {
        return LOADED
    }

    override fun pauseBanner() {}

    override fun resumeBanner() {}

    override fun destroyBanner() {}
}