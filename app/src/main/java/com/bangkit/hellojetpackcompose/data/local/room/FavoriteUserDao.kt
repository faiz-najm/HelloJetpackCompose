package com.bangkit.hellojetpackcompose.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bangkit.hellojetpackcompose.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: FavoriteUserEntity)

    @Update
    suspend fun updateUser(user: FavoriteUserEntity)

    @Delete
    suspend fun deleteUser(user: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_user ORDER BY username DESC")
    fun getAllFavoriteUser(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite_user WHERE username = :username")
    fun getFavoriteUser(username: String): Flow<List<FavoriteUserEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_user WHERE username = :username LIMIT 1)")
    fun isFavoriteUser(username: String): Flow<Boolean>
}