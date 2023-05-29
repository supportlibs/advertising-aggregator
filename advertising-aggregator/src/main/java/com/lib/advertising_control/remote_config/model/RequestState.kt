package com.lib.advertising_control.remote_config.model

import androidx.annotation.Keep

@Keep
sealed class RequestState<out T> {
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val exception: Exception) : RequestState<Nothing>()

    fun toUiState(): UiState<T> {
        return when (this) {
            is Success -> UiState.Success(this.data)
            is Error -> UiState.Error(this.exception.message)
        }
    }
}