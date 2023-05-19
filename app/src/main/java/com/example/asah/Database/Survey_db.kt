package com.example.asah.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Survey_db(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "tinggi") val tinggi: Double,
    @ColumnInfo(name = "berat") val berat: Double
)