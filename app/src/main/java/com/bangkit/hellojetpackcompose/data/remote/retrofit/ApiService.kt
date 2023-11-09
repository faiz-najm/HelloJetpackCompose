package com.bangkit.hellojetpackcompose.data.remote.retrofit

import com.bangkit.hellojetpackcompose.data.remote.response.GithubResponse
import com.bangkit.hellojetpackcompose.data.remote.response.Items
import com.bangkit.hellojetpackcompose.model.User
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    suspend fun getListUser(
        @Query("q") q: String
    ): GithubResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): User

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<Items>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<Items>
}
