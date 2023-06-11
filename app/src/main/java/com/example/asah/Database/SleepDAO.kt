package com.example.asah.Database

import androidx.room.*

@Dao
interface SleepDAO {
    @Query("SELECT * FROM sleep")
    fun getSleep():List<Sleep>

    @Query("SELECT * FROM sleep WHERE date == :date")
    fun getSleepbyDate(date: String):List<Sleep>

    @Insert
    fun insert(screen: Sleep)

    @Update
    fun update(screen: Sleep)

    @Delete
    fun delete(screen: Sleep)
}