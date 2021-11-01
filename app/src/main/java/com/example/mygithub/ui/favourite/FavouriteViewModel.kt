package com.example.mygithub.ui.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mygithub.data.local.FavouriteUser
import com.example.mygithub.data.local.FavouriteUserDao
import com.example.mygithub.data.local.UserDatabase

class FavouriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavouriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavouriteUser>>? {
        return userDao?.getFavouriteUser()

    }


}