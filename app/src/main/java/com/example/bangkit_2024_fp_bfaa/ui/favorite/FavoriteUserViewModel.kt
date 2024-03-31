package com.example.bangkit_2024_fp_bfaa.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_fp_bfaa.data.database.FavoriteUser
import com.example.bangkit_2024_fp_bfaa.data.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _listFavUser = MutableLiveData<List<FavoriteUser>>()
    val listFavUser: LiveData<List<FavoriteUser>> = _listFavUser

    fun getAllFavUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavUser()
}