package com.lib.advertising_control.remote_config.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
enum class AdType {
    @SerializedName("admob")
    ADMOB,
    @SerializedName("none")
    NONE,
    @SerializedName("facebook")
    FACEBOOK,
    @SerializedName("applovin")
    APPLOVIN
}