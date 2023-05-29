package com.lib.advertising_control.remote_config.managers.base

import com.lib.advertising_control.remote_config.model.InterstitialShowState
import kotlinx.coroutines.flow.Flow

interface InterstitialShowManager {

    fun showInterstitial(): Flow<InterstitialShowState>
}