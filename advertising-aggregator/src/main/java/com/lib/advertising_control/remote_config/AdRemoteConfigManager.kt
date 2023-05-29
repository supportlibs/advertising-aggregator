package com.lib.advertising_control.remote_config

import android.util.Log
import androidx.annotation.Keep
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigClientException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lib.advertising_control.R
import com.lib.advertising_control.remote_config.model.ConfigAdModel
import com.lib.advertising_control.remote_config.model.RemoteConfigFetchStatus
import com.lib.advertising_control.remote_config.model.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Keep
class AdRemoteConfigManager {

    val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
        val configSettings = FirebaseRemoteConfigSettings.Builder().setFetchTimeoutInSeconds(0).build()
        setConfigSettingsAsync(configSettings)
        setDefaultsAsync(R.xml.remote_config_defaults)
    }

    private val gson = Gson()

    fun getAdRequest(key: String): RequestState<List<ConfigAdModel>> {
        try {
            Log.d("config", "getAdRequest: ${remoteConfig.getString(key)}")
            val typeToken = object : TypeToken<List<ConfigAdModel>>() {}.type
            val parsedArray = gson.fromJson<List<ConfigAdModel>>(remoteConfig.getString(key), typeToken)

            parsedArray.forEach { Log.d("config", "getAdRequest: parsed array : $it") }

            return RequestState.Success(parsedArray)

        } catch (e: SocketTimeoutException) {
            Log.e("AD_CONFIG", "${e.message}")
            return RequestState.Error(e)

        } catch (e: UnknownHostException) {
            Log.e("AD_CONFIG", "${e.message}")
            return RequestState.Error(e)

        } catch (e: FirebaseRemoteConfigClientException) {
            Log.e("AD_CONFIG", "${e.message}")
            return RequestState.Error(e)

        } catch (e: Exception) {
            Log.e("AD_CONFIG", "${e.message}")
            FirebaseCrashlytics.getInstance().recordException(Throwable(e.message))
            return RequestState.Error(e)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadInfoFromRemoteConfig() = callbackFlow {
        remoteConfig.fetch(0).addOnCompleteListener {
            if (it.isSuccessful) {
                remoteConfig.activate()
                    .addOnCompleteListener { trySend(RemoteConfigFetchStatus.FETCH_COMPLETED) }
            }
        }.addOnFailureListener { trySend(RemoteConfigFetchStatus.FAILED) }
        awaitClose {}
    }
}