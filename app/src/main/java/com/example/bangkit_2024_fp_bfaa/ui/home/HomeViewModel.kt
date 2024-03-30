package com.example.bangkit_2024_fp_bfaa.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.bangkit_2024_fp_bfaa.data.response.*
import com.example.bangkit_2024_fp_bfaa.data.retrofit.ApiConfig
import com.example.bangkit_2024_fp_bfaa.ui.setting.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.*

class HomeViewModel(private val pref: SettingPreferences): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<UserResponse>>()
    val listUser: LiveData<List<UserResponse>> = _listUser

    init {
        findUsers(USER_ID)
    }

    fun findUsers(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object: Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listUser.value = response.body()?.items
                    }
                    if (response.body()?.items!!.isNotEmpty()){
                        Log.d("Nope", "Gak ada")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    companion object {
        private const val TAG = "HomeViewModel"
        private const val USER_ID = "Arif"
    }
}