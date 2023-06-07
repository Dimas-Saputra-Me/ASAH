package com.example.asah.Database

import androidx.room.*

@Dao
interface ScreenDAO {
    @Query("SELECT * FROM screen")
    fun getScreen():List<Screen>

    @Query("SELECT * FROM screen WHERE date == :date")
    fun getScreenbyDate(date: String):List<Screen>

    @Insert
    fun insert(screen: Screen)

    @Update
    fun update(screen: Screen)

    @Delete
    fun delete(screen: Screen)
}