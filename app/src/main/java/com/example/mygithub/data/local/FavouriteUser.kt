package com.example.mygithub.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourite_user")
data class FavouriteUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String,
    val html_url: String
): Serializable
