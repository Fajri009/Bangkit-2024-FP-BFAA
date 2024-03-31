package com.example.bangkit_2024_fp_bfaa.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bangkit_2024_fp_bfaa.data.database.FavoriteUser
import com.example.bangkit_2024_fp_bfaa.data.database.FavoriteUserDao
import com.example.bangkit_2024_fp_bfaa.data.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executoreService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun insertFavUser(user: FavoriteUser) {
        executoreService.execute {
            mFavoriteUserDao.insertFavUser(user)
        }
    }

    fun getAllFavUser(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavUser()

    fun deleteFavUser(id: Int) {
        executoreService.execute {
            mFavoriteUserDao.removeFavUser(id)
        }
    }
}