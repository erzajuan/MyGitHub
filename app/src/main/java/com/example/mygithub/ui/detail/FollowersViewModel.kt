package com.example.mygithub.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.api.ApiConfig
import com.example.mygithub.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _followersData = MutableLiveData<ArrayList<User>>()
    fun getFollowersData(): LiveData<ArrayList<User>> = _followersData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollowersData(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followersData.postValue(response.body())
                    } else {
                        Log.e(ContentValues.TAG, "onResponseFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }

            })
    }
}