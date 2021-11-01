package com.example.mygithub.api


import com.example.mygithub.data.model.DetailResponse
import com.example.mygithub.data.model.User
import com.example.mygithub.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}