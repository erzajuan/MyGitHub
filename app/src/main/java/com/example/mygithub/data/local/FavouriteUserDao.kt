package com.example.mygithub.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteUserDao {
    @Insert
    suspend fun addFavourite(favouriteUser: FavouriteUser)

    @Query("SELECT * FROM favourite_user")
    fun getFavouriteUser(): LiveData<List<FavouriteUser>>

    @Query("SELECT count(*) FROM favourite_user WHERE favourite_user.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query( "DELETE FROM favourite_user where favourite_user.id = :id")
    suspend fun removeFromFavourite(id: Int): Int
}