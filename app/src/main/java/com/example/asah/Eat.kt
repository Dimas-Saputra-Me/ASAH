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

    private var selectedKarbohidrat: Double? = null
    private var selectedProtein: Double? = null
    private var selectedSerat: Double? = null

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

        // Get Karbohidrat
        val karbohidratDropdown: AutoCompleteTextView = binding.inputKarbohidrat
        karbohidratDropdown.setOnItemClickListener { parent, view, position, id ->
            // TODO: change cal value
            val positionToKarbohidrat: Array<Double> = arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
            this.selectedKarbohidrat = positionToKarbohidrat[position]
        }

        // Get Protein
        val ProteinDropdown: AutoCompleteTextView = binding.inputProtein
        ProteinDropdown.setOnItemClickListener { parent, view, position, id ->
            // TODO: change cal value
            val positionToProtein: Array<Double> = arrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0)
            this.selectedProtein = positionToProtein[position]
        }

        // Get Serat
        val SeratDropdown: AutoCompleteTextView = binding.inputSerat
        SeratDropdown.setOnItemClickListener { parent, view, position, id ->
            // TODO: change cal value
            val positionToSerat: Array<Double> = arrayOf(1.0, 2.0, 3.0)
            this.selectedSerat = positionToSerat[position]
        }

        binding.btnAddMakan.setOnClickListener {
            db.MakananDAO().insert(Makanan(
                // TODO : diganti yaa
                // TODO : nanti pake if else kalau bisa
                karbohidrat = this.selectedKarbohidrat!!,
                protein = this.selectedProtein!!,
                serat = this.selectedSerat!!,
                date = getDate()
            ))

            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}

