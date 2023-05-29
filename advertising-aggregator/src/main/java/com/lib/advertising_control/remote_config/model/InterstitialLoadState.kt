package com.lib.advertising_control.remote_config.model

import com.lib.advertising_control.remote_config.managers.base.BaseAdObject

sealed class InterstitialLoadState(
    open val adObject: BaseAdObject
) {
    data class Success(override val adObject: BaseAdObject) : InterstitialLoadState(adObject)
    data class Loading(override val adObject: BaseAdObject = BaseAdObject.NoneAdObject) : InterstitialLoadState(adObject)
    data class Completed(override val adObject: BaseAdObject = BaseAdObject.NoneAdObject) : InterstitialLoadState(adObject)
}
