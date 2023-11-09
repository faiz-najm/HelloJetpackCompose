package com.bangkit.hellojetpackcompose.compose.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hellojetpackcompose.data.local.entity.FavoriteUserEntity
import com.bangkit.hellojetpackcompose.repo.AppRepository
import com.bangkit.hellojetpackcompose.compose.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<FavoriteUserEntity>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteUserEntity>>>
        get() = _uiState

    init {
        getAllFavoriteUser()
    }

    fun getAllFavoriteUser() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getFavoriteUser().first()
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun searchFavoriteUser(searchQuery: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getFavoriteUser().first()
                    .filter { it.username.contains(searchQuery, ignoreCase = true) }

                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}