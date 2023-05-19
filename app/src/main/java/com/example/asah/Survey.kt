package com.example.asah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.asah.Database.Survey_db
import com.example.asah.Database.asahDatabase
import com.example.asah.databinding.ActivitySurveyBinding

class Survey : AppCompatActivity() {

    lateinit var binding: ActivitySurveyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide Top Bar
        this.getSupportActionBar()?.hide()

        //inisialisasi Database
        val db = Room.databaseBuilder(applicationContext, asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        binding.submit.setOnClickListener {

            if(db.Survey_dbDAO().getSurvey().isEmpty()){
                db.Survey_dbDAO().insert(
                    Survey_db(
                    tinggi = binding.inputTinggi.text.toString().toDouble(),
                    berat = binding.inputBerat.text.toString().toDouble()
                )
                )
            } else {
                db.Survey_dbDAO().update(Survey_db(
                    id = 1,
                    tinggi = binding.inputTinggi.text.toString().toDouble(),
                    berat = binding.inputBerat.text.toString().toDouble()
                ))
            }

            val intent = Intent(this, MainPage::class.java)
            startActivity(intent)
        }

    }


}