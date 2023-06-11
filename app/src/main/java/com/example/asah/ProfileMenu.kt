package com.example.asah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.asah.Database.Makanan
import com.example.asah.Database.Survey_db
import com.example.asah.Database.asahDatabase
import com.example.asah.databinding.ActivityProfileMenuBinding

class ProfileMenu : AppCompatActivity() {

    lateinit var binding: ActivityProfileMenuBinding
    var todayKCal: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inisialisasi Database
        val db = Room.databaseBuilder(this, asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        // Set IMT
        val surveys: List<Survey_db> = db.Survey_dbDAO().getSurvey()
        val imt = (surveys[0].berat * 10000) / (surveys[0].tinggi * surveys[0].tinggi)
        binding.imtData.setText("$imt")

        // Set KCal harian
        val makanan: List<Makanan> = db.MakananDAO().getMakananbyDate(date = getDate())
        for(m in makanan){
            todayKCal += (m.karbohidrat + m.protein + m.serat)
        }
        binding.kcalData.setText("$todayKCal")

        // Bottom Bar Listener
        binding.bottomNavigation.menu.findItem(R.id.nav_bottom_profile).isChecked = true
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_bottom_home -> {
                    val intent = Intent(this, MainPage::class.java)
                    startActivity(intent)
                }
                R.id.nav_bottom_activity -> {
                    val intent = Intent(this, MenuRutinitas::class.java)
                    startActivity(intent)
                }
                R.id.nav_bottom_settings -> {
                    // TODO
                }
                R.id.nav_bottom_profile -> {
                    // This page
                }
            }

            true
        }

    }
}