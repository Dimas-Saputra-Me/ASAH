package com.example.asah.Database

import androidx.room.*

@Dao
interface Survey_dbDAO {
    @Query("SELECT * FROM survey_db")
    fun getSurvey():List<Survey_db>

    @Insert
    fun insert(survey_db: Survey_db)

    @Update
    fun update(survey_db: Survey_db)

    @Delete
    fun delete(survey_db: Survey_db)

}
