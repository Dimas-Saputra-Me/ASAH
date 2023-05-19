package com.example.asah.Database

import androidx.room.*

@Dao
interface MakananDAO {
    @Query("SELECT * FROM makanan")
    fun getMakanan():List<Makanan>

    @Query("SELECT * FROM makanan WHERE date == :date")
    fun getMakananbyDate(date: String):List<Makanan>

    @Insert
    fun insert(makanan: Makanan)

    @Update
    fun update(makanan: Makanan)

    @Delete
    fun delete(makanan: Makanan)

}