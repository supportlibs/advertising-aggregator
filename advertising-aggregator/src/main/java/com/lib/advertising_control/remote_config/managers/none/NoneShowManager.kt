package com.lib.advertising_control.remote_config.managers.none

import com.lib.advertising_control.remote_config.managers.base.InterstitialShowManager
import com.lib.advertising_control.remote_config.model.InterstitialShowState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NoneShowManager : InterstitialShowManager {

    override fun showInterstitial(): Flow<InterstitialShowState> {
        return flowOf(InterstitialShowState.SHOWED)
    }
}