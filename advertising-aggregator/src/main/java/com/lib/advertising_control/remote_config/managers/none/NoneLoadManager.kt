package com.lib.advertising_control.remote_config.managers.none

import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
import com.lib.advertising_control.remote_config.managers.base.InterstitialLoadManager
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf

class NoneLoadManager : InterstitialLoadManager() {

    override fun loadInterstitial(adModel: ConfigAdModel) = flowOf(BaseAdObject.NoneAdObject)

}