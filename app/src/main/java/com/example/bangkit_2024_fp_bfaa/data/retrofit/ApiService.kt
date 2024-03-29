package com.example.bangkit_2024_fp_bfaa.data.retrofit

import com.example.bangkit_2024_fp_bfaa.data.response.DetailUserResponse
import com.example.bangkit_2024_fp_bfaa.data.response.GithubResponse
import com.example.bangkit_2024_fp_bfaa.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") user: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>
}