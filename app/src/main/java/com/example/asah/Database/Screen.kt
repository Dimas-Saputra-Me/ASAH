package com.example.asah.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Screen(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "jam") val jam: Int,
    @ColumnInfo(name = "date") val date: String
)