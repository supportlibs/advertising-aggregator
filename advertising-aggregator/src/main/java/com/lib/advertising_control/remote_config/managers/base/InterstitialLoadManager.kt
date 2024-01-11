package com.lib.advertising_control.remote_config.managers.base

import com.lib.advertising_control.remote_config.model.ConfigAdModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

abstract class InterstitialLoadManager {

    abstract fun loadInterstitial(adModel: ConfigAdModel): Flow<BaseAdObject>

}