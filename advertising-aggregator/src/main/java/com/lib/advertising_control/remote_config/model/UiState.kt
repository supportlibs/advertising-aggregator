package com.lib.advertising_control.remote_config.model

import androidx.annotation.Keep

@Keep
sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val errorMessage: String?) : UiState<Nothing>()
}