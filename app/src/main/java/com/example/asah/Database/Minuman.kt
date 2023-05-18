package com.example.asah.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Minuman(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "intensitas") val intensitas: Int,
    @ColumnInfo(name = "date") val date: String
)