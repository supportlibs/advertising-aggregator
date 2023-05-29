package com.lib.advertising_control.remote_config.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ConfigAdModel(
    @SerializedName("ads_provider")
    val adsProvider: AdType,
    @SerializedName("is_test")
    val isTest: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("priority")
    val priority: Int
)
