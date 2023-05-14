package com.example.asah.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Profile::class, Survey_db::class, Minuman::class], version = 1)
abstract class asahDatabase: RoomDatabase() {
    abstract fun ProfileDAO(): ProfileDAO
    abstract fun Survey_dbDAO(): Survey_dbDAO
    abstract fun MinumanDAO(): MinumanDAO
}