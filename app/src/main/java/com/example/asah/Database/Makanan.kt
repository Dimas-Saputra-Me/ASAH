package com.example.asah.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Makanan(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "karbohidrat") val karbohidrat: Double,
    @ColumnInfo(name = "protein") val protein: Double,
    @ColumnInfo(name = "serat") val serat: Double,
    @ColumnInfo(name = "date") val date: String
)