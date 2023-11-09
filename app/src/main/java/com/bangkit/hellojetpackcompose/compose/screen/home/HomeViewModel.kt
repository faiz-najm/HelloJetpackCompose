package com.bangkit.hellojetpackcompose.compose.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hellojetpackcompose.data.remote.response.Items
import com.bangkit.hellojetpackcompose.repo.AppRepository
import com.bangkit.hellojetpackcompose.compose.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Items>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Items>>>
        get() = _uiState

    init {
        getAllUser()
    }

    fun getAllUser(s: String = "a") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getListUser(s.ifEmpty { "a" })
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}