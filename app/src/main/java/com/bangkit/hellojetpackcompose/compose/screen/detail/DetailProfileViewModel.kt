package com.bangkit.hellojetpackcompose.compose.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hellojetpackcompose.data.local.entity.FavoriteUserEntity
import com.bangkit.hellojetpackcompose.model.User
import com.bangkit.hellojetpackcompose.repo.AppRepository
import com.bangkit.hellojetpackcompose.compose.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<User>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<User>>
        get() = _uiState

    val username: String = savedStateHandle.get<String>(USERNAME_SAVED_STATE_KEY)!!
    val isFavorite = repository.isFavoriteUser(username)

    fun getDetailUser() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getDetailUser(username)
                _uiState.value = UiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun insertFavoriteUser(items: FavoriteUserEntity) {
        viewModelScope.launch {
            repository.insertFavoriteUser(items)
        }
    }

    fun deleteFavoriteUser(items: FavoriteUserEntity) {
        viewModelScope.launch {
            repository.deleteFavoriteUser(items)
        }
    }

    companion object {
        private const val USERNAME_SAVED_STATE_KEY = "username"
    }
}