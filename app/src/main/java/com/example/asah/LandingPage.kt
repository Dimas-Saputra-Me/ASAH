package com.example.asah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.room.Room
import com.example.asah.Database.Profile
import com.example.asah.Database.asahDatabase
import com.example.asah.databinding.ActivityLandingPageBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

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

        // Date input
        // TODO : Halooo, nanti ngedit tanggal di sini yaa
        val inputDatePicker: com.google.android.material.textfield.TextInputEditText =
            findViewById(R.id.input_date)

        // when text field button is clicked
        inputDatePicker.setOnClickListener {
            // Initiation date picker with
            // MaterialDatePicker.Builder.datePicker()
            // and building it using build()
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                val tvDate: com.google.android.material.textfield.TextInputEditText = findViewById(R.id.input_date)
                tvDate.setText(date)
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener { }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener { }
        }


        // Sign-Up Button
        binding.btnSignUp.setOnClickListener {

            if(db.ProfileDAO().getProfile().isEmpty()){
                db.ProfileDAO().insert(Profile(
                    nama = binding.inputNama.text.toString(),
                    gender = binding.inputGender.text.toString() == "Male",
                    tanggal_lahir = inputDatePicker.text.toString()
                ))
            } else {
                db.ProfileDAO().update(Profile(
                    id = 1,
                    nama = binding.inputNama.text.toString(),
                    gender = binding.inputGender.text.toString() == "Male",
                    tanggal_lahir = inputDatePicker.text.toString()
                ))
            }

            val intent = Intent(this, Survey::class.java)
            startActivity(intent)

        }
    }

}