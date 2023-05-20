package com.example.asah.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Olahraga(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "waktu") val waktu: Boolean,
    @ColumnInfo(name = "kalori") val kalori: Double,
    @ColumnInfo(name = "durasi") val durasi: Double,
    @ColumnInfo(name = "date") val date: String
)