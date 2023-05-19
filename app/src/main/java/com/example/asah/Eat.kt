package com.example.asah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.asah.Database.Makanan
import com.example.asah.Database.asahDatabase
import com.example.asah.databinding.ActivityEatBinding
import java.util.*

class Eat : AppCompatActivity() {
    lateinit var binding: ActivityEatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // action bar title
        supportActionBar?.title = "Menu Makanan"

        // Back Button
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        // inisialisasi Database
        val db = Room.databaseBuilder(applicationContext, asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        binding.btnAddMakan.setOnClickListener {
            db.MakananDAO().insert(Makanan(
                karbohidrat = binding.inputKarbohidrat.text.toString().toDouble(),
                protein = binding.inputProtein.text.toString().toDouble(),
                serat = binding.inputSerat.text.toString().toDouble(),
                date = getDate()
            ))

            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    fun getDate() : String {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR).toString()
        val month = c.get(Calendar.MONTH).toString()
        val day = c.get(Calendar.DAY_OF_WEEK)

        val hari = arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

        val date = "${hari[day-1]} - $month - $year"

        return date
    }

}

