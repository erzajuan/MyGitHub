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

class FollowingViewModel : ViewModel() {
    private val _followingData = MutableLiveData<ArrayList<User>>()
    fun getFollowingData(): LiveData<ArrayList<User>> = _followingData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollowingData(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _followingData.postValue(response.body())
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