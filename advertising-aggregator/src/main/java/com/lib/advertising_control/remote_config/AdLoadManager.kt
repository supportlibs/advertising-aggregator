package com.lib.advertising_control.remote_config

import android.app.Activity
import com.lib.advertising_control.remote_config.managers.admob.AdmobLoadManager
import com.lib.advertising_control.remote_config.managers.applovin.ApplovinLoadManager
import com.lib.advertising_control.remote_config.managers.base.BaseAdObject
import com.lib.advertising_control.remote_config.managers.base.InterstitialLoadManager
import com.lib.advertising_control.remote_config.managers.none.NoneLoadManager
import com.lib.advertising_control.remote_config.model.AdType.ADMOB
import com.lib.advertising_control.remote_config.model.AdType.APPLOVIN
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import com.lib.advertising_control.remote_config.model.InterstitialLoadState.*
import com.lib.advertising_control.remote_config.model.RemoteConfigFetchStatus
import com.lib.advertising_control.remote_config.model.RemoteConfigFetchStatus.FETCH_COMPLETED
import com.lib.advertising_control.remote_config.model.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

public class AdLoadManager(
    private val activity: Activity,
    private val adBlock: String
) {

    private val remoteConfigManager: AdRemoteConfigManager = AdRemoteConfigManager()
    private var interstitialLoadManager: InterstitialLoadManager = NoneLoadManager()

    fun loadAdFlow(scope: CoroutineScope, isTestAdmob: Boolean = false) = flow {
        remoteConfigManager.loadInfoFromRemoteConfig().collect { remoteConfigLoadStatus ->
            if (remoteConfigLoadStatus == FETCH_COMPLETED) {
                when (val config = remoteConfigManager.getAdRequest(adBlock)) {
                    is RequestState.Error -> { error("Failed to get data") }
                    is RequestState.Success -> {
                        for (adModel in config.data.sortedBy { it.priority }) {
                            interstitialLoadManager = when(adModel.adsProvider) {
                                ADMOB -> AdmobLoadManager(activity, isTestAdmob)
                                APPLOVIN -> ApplovinLoadManager(activity)
                                else -> NoneLoadManager()
                            }
                            try {
                                val loadResult =
                                    loadAdForAvailableAdProviderFlow(adModel, scope).single()
                                emit(Success(loadResult))
                                return@collect
                            } catch (e: Exception) {
                                continue
                            }
                        }
                        emit(Completed())
                    }
                }
            } else {
                emit(Completed())
            }
        }
    }

    private fun loadAdForAvailableAdProviderFlow(
        adModel: ConfigAdModel,
        scope: CoroutineScope
    ) = flow {
        when (val result = interstitialLoadManager.loadInterstitial(adModel, scope)) {
            is BaseAdObject.Error -> error("Ad failed to load. Go next")
            else -> emit(result)
        }
    }

}