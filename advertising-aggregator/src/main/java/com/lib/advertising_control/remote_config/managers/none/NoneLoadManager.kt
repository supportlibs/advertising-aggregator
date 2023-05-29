package com.lib.advertising_control.remote_config.managers.none

import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
import com.lib.advertising_control.remote_config.managers.base.InterstitialLoadManager
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.CoroutineScope

class NoneLoadManager : InterstitialLoadManager() {

    override suspend fun loadInterstitial(adModel: ConfigAdModel, scope: CoroutineScope): BaseAdObject {
        return BaseAdObject.NoneAdObject
    }

}