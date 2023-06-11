package com.example.asah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.asah.Database.Screen
import androidx.room.Room
import com.example.asah.Database.*
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
        val makanan: List<Makanan> = db.MakananDAO().getMakanan()
        val olahraga: List<Olahraga> = db.OlahragaDAO().getOlahraga()
        val screen: List<Screen> = db.ScreenDAO().getScreen()
        val sleep: List<Sleep> = db.SleepDAO().getSleep()

        var dbText = " "

        if(profile.isNotEmpty()){
            dbText += "${profile[0].nama} | ${profile[0].gender} | ${profile[0].tanggal_lahir}\n"
        }
        if(surveys.isNotEmpty()){
            dbText += "${surveys[0].tinggi} | ${surveys[0].berat} |\n"
        }

        for(m in makanan){
            dbText += "${m.karbohidrat} | ${m.protein} | ${m.serat} | ${m.date} |\n"
        }

        for(o in olahraga){
            dbText += "${o.id} | ${o.waktu} | ${o.kalori} | ${o.durasi} | ${o.date} |\n"
        }

        for(s in screen){
            dbText += "${s.id} | ${s.jam} | ${s.date} |\n"
        }

        for(s in sleep){
            dbText += "${s.id} | ${s.menit} | ${s.date} |\n"
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