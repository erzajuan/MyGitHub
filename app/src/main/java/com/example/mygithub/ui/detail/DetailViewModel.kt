package com.example.mygithub.ui.detail

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygithub.api.ApiConfig
import com.example.mygithub.data.local.FavouriteUser
import com.example.mygithub.data.local.FavouriteUserDao
import com.example.mygithub.data.local.UserDatabase
import com.example.mygithub.data.model.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailResponse>()

    private var userDao: FavouriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    private val _isLoading = MutableLiveData<Boolean>()

    fun setUserDetail(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    } else {
                        Log.e(TAG, "onResponseFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getUserDetail(): LiveData<DetailResponse> {
        return user
    }

    fun addFavorite(username: String, id: Int, avatar_url: String, html_url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavouriteUser(
                username,
                id,
                avatar_url,
                html_url
            )
            userDao?.addFavourite(user)
        }
    }

    suspend fun  checkUser(id: Int) = userDao?.checkUser(id)

    fun  removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavourite(id)
        }
    }
}