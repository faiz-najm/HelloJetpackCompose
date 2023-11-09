package com.bangkit.hellojetpackcompose.repo

import androidx.lifecycle.LiveData
import com.bangkit.hellojetpackcompose.data.local.entity.FavoriteUserEntity
import com.bangkit.hellojetpackcompose.data.local.room.FavoriteUserDao
import com.bangkit.hellojetpackcompose.data.remote.response.Items
import com.bangkit.hellojetpackcompose.data.remote.retrofit.ApiService
import com.bangkit.hellojetpackcompose.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val favoriteUserDao: FavoriteUserDao,
    private val apiService: ApiService
) {

    suspend fun getListUser(query: String): List<Items> {
        return withContext(Dispatchers.IO) {
            val users = apiService.getListUser(query)
            users.items
        }
    }

    suspend fun getDetailUser(username: String): User {
        return withContext(Dispatchers.IO) {
            apiService.getDetailUser(username)
        }
    }

    fun getFavoriteUser() = favoriteUserDao.getAllFavoriteUser()
    fun isFavoriteUser(username: String) = favoriteUserDao.isFavoriteUser(username)

    suspend fun insertFavoriteUser(items: FavoriteUserEntity) {
        favoriteUserDao.insertUser(
            FavoriteUserEntity(
                username = items.username,
                name = items.name,
                avatarUrl = items.avatarUrl
            )
        )
    }

    suspend fun deleteFavoriteUser(items: FavoriteUserEntity) {
        favoriteUserDao.deleteUser(
            FavoriteUserEntity(
                username = items.username,
                name = items.name,
                avatarUrl = items.avatarUrl
            )
        )
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(
            favoriteUserDao: FavoriteUserDao,
            apiService: ApiService
        ) = instance ?: synchronized(this) {
            instance ?: AppRepository(favoriteUserDao, apiService).also { instance = it }
        }
    }
}