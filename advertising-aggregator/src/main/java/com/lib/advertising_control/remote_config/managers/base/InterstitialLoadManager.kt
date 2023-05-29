package com.lib.advertising_control.remote_config.managers.base

import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.CoroutineScope

abstract class InterstitialLoadManager {

    abstract suspend fun loadInterstitial(adModel: ConfigAdModel, scope: CoroutineScope): BaseAdObject

}