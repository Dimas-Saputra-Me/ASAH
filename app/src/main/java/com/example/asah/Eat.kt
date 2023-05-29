package com.example.asah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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

        // TODO : mungkin butuh sinkronisasi ulang. mohon bantuaanya sepuh
        // Dropdown Karbohidrat
        val karboItems = listOf(
            "none",
            "Nasi Putih (? kcal)",
            "Kentang (? kcal)",
            "Cereal (? kcal)",
            "Nasi Merah (? kcal)",
            "Mie (? kcal)"
        )
        val karboAdapter = ArrayAdapter(this, R.layout.list_dropdown, karboItems)
        (binding.inputKarbohidrat as? AutoCompleteTextView)?.setAdapter(karboAdapter)

        // Dropdown Protein
        val proteinItem = listOf(
            "none",
            "Ayam (? kcal)",
            "Daging Sapi (? kcal)",
            "Ikan (? kcal)",
            "Tahu/Tempe (? kcal)",
            "Telur (? kcal)",
            "Susu (? kcal)"
        )
        val proteinAdapter = ArrayAdapter(this, R.layout.list_dropdown, proteinItem)
        (binding.inputProtein as? AutoCompleteTextView)?.setAdapter(proteinAdapter)

        // Dropdown Serat
        val seratItem = listOf(
            "none",
            "Sayur (? kcal)",
            "Buah (? kcal)"
        )
        val seratAdapter = ArrayAdapter(this, R.layout.list_dropdown, seratItem)
        (binding.inputSerat as? AutoCompleteTextView)?.setAdapter(seratAdapter)

        binding.btnAddMakan.setOnClickListener {
            db.MakananDAO().insert(Makanan(
                // TODO : diganti yaa
                // TODO : nanti pake if else kalau bisa
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
        val day_week = c.get(Calendar.DAY_OF_WEEK)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hari = arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

        val date = "${hari[day_week-1]} - $day/${month.toInt() + 1}/$year"

        return date
    }

}

