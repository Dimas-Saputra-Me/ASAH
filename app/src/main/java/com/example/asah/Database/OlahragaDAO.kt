package com.example.asah.Database

import androidx.room.*

@Dao
interface OlahragaDAO {
    @Query("SELECT * FROM olahraga")
    fun getOlahraga():List<Olahraga>

    @Query("SELECT * FROM olahraga WHERE date == :date AND waktu == :waktu")
    fun getOlahragabyDateandWaktu(date: String, waktu: Boolean):List<Olahraga>

    @Insert
    fun insert(olahraga: Olahraga)

    @Update
    fun update(olahraga: Olahraga)

    @Delete
    fun delete(olahraga: Olahraga)

}