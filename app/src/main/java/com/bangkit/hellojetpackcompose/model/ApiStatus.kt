package com.bangkit.hellojetpackcompose.model

sealed class ApiStatus<out R> private constructor() {
    data class Success<out T>(val data: T) : ApiStatus<T>()
    data class Error(val error: String) : ApiStatus<Nothing>()
    object Loading : ApiStatus<Nothing>()
}