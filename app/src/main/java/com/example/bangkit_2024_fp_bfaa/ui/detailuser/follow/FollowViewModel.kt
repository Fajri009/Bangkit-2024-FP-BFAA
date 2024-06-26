package com.example.bangkit_2024_fp_bfaa.ui.detailuser.follow

import android.util.Log
import androidx.lifecycle.*
import com.example.bangkit_2024_fp_bfaa.data.response.UserResponse
import com.example.bangkit_2024_fp_bfaa.data.retrofit.ApiConfig
import retrofit2.*

class FollowViewModel: ViewModel() {
    private val _listFollowers = MutableLiveData<List<UserResponse>>()
    val listFollowers: LiveData<List<UserResponse>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<UserResponse>>()
    val listFollowing: LiveData<List<UserResponse>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object: Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listFollowers.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(user: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(user)
        client.enqueue(object: Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listFollowing.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}", )
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}