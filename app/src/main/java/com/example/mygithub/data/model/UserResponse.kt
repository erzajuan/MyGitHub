package com.example.mygithub.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("items")
    val items: ArrayList<User>
)

data class User(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("html_url")
    val html_url: String
)
