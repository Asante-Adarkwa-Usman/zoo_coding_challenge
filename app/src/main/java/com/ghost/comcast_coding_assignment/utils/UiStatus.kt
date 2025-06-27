package com.ghost.comcast_coding_assignment.utils

sealed class UiStatus<out T> {
   //Handling state status
    data object Loading: UiStatus<Nothing>()
    data class Success<T>(val data: T): UiStatus<T>()
    data class Error(val message: String): UiStatus<Nothing>()
}