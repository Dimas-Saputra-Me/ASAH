package com.example.asah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.room.Room
import com.example.asah.Database.Profile
import com.example.asah.Database.asahDatabase
import com.example.asah.databinding.ActivityLandingPageBinding

class LandingPage : AppCompatActivity() {
    lateinit var binding: ActivityLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide Top Bar
        this.getSupportActionBar()?.hide()

        // TODO: Continue
        // One-Time-Fill Feature (BETA)
        val db = Room.databaseBuilder(applicationContext, asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()
        if(db.ProfileDAO().getProfile().isNotEmpty() and db.Survey_dbDAO().getSurvey().isNotEmpty()){
            val intent = Intent(this, MainPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Dropdown
        val items = listOf("Male", "Female")
        val adapter = ArrayAdapter(this, R.layout.list_dropdown, items)
        (binding.inputGender as? AutoCompleteTextView)?.setAdapter(adapter)


        // Sign-Up Button
        binding.btnSignUp.setOnClickListener {

            if(db.ProfileDAO().getProfile().isEmpty()){
                db.ProfileDAO().insert(Profile(
                    nama = binding.inputNama.text.toString(),
                    gender = binding.inputGender.text.toString() == "Male"
                ))
            } else {
                db.ProfileDAO().update(Profile(
                    id = 1,
                    nama = binding.inputNama.text.toString(),
                    gender = binding.inputGender.text.toString() == "Male"
                ))
            }

            val intent = Intent(this, Survey::class.java)
            startActivity(intent)
        }
    }
}