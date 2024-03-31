package com.example.bangkit_2024_fp_bfaa.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavUser(user: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser ORDER BY login ASC")
    fun getAllFavUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun getFavUserById(id: Int): LiveData<FavoriteUser>

    @Query("DELETE FROM FavoriteUser WHERE FavoriteUser.id = :id")
    fun removeFavUser(id: Int)
}