package com.example.asah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.asah.Database.Profile
import com.example.asah.Database.Survey_db
import com.example.asah.Database.asahDatabase
import com.example.asah.databinding.ActivityDatabaseTempBinding

class DatabaseTemp : AppCompatActivity() {

    lateinit var binding: ActivityDatabaseTempBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseTempBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inisialisasi Database
        val db = Room.databaseBuilder(applicationContext, asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        // Get Data
        val profile: List<Profile> = db.ProfileDAO().getProfile()
        val surveys: List<Survey_db> = db.Survey_dbDAO().getSurvey()

        var dbText = " "

        if(profile.isNotEmpty()){
            dbText += "${profile[0].nama} | ${profile[0].gender}\n"
        }
        if(surveys.isNotEmpty()){
            dbText += "${surveys[0].tinggi} | ${surveys[0].berat} | ${surveys[0].olg_jam} | ${surveys[0].olg_minggu} |\n"
        }


        binding.dataTmp.text = dbText

        // Back Button
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}