package com.example.asah.Database

import androidx.room.*

@Dao
interface ProfileDAO {
    @Query("SELECT * FROM profile")
    fun getProfile():List<Profile>

    @Insert
    fun insert(profile: Profile)

    @Update
    fun update(profile: Profile)

    @Delete
    fun delete(profile: Profile)

}
