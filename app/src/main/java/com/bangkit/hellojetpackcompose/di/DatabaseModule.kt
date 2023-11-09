package com.bangkit.hellojetpackcompose.di

import android.content.Context
import com.bangkit.hellojetpackcompose.data.local.room.FavoriteUserDao
import com.bangkit.hellojetpackcompose.data.local.room.FavoriteUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoriteUserDatabase {
        return FavoriteUserDatabase.getInstance(context)
    }

    @Provides
    fun provideUserDao(database: FavoriteUserDatabase) : FavoriteUserDao = database.userDao()

}