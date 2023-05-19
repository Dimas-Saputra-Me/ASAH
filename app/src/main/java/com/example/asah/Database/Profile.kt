package com.example.asah.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "nama") val nama: String,
    @ColumnInfo(name = "gender") val gender: Boolean,
    @ColumnInfo(name = "tanggal_lahir") val tanggal_lahir: String
)