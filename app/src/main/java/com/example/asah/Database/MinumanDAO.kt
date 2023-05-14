package com.example.asah.Database

import androidx.room.*

@Dao
interface MinumanDAO {
    @Query("SELECT * FROM minuman")
    fun getMinuman():List<Minuman>

    @Insert
    fun insert(minuman: Minuman)

    @Update
    fun update(minuman: Minuman)

    @Delete
    fun delete(minuman: Minuman)

}