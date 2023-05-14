package com.example.asah.DrinkFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.asah.Database.Minuman
import com.example.asah.Database.Profile
import com.example.asah.Database.asahDatabase
import com.example.asah.R
import kotlin.properties.Delegates

class DrinkTrack : Fragment() {
    var selectedGlass by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_drink_track, container, false)

        // inisialisasi Database
        val db = Room.databaseBuilder(requireContext(), asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        // Update Text & Progress
        updatePage(view, db)

        // btn add drink
        val btn_add = view.findViewById<ImageView>(R.id.btn_add_minum)
        btn_add.setOnClickListener {
            // inflate dialog
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.add_drink_dialog, null)

            // set drop down
            val items = listOf(
                "Gelas kecil (250 mL)",
                "Gelas besar (450 mL)",
                "Botol (600 mL)"
            )
            val arrayAdapter = ArrayAdapter(
                requireActivity(),
                R.layout.list_dropdown,
                items
            )
            val typeDropDown: AutoCompleteTextView = dialogView.findViewById<AutoCompleteTextView>(R.id.add_drink)

            typeDropDown.setOnItemClickListener { parent, view, position, id ->
                val positionToMl: Array<Int> = arrayOf(250, 450, 600)
                 this.selectedGlass = positionToMl[position]
            }

            //launch
            typeDropDown.setAdapter(arrayAdapter)

            // AlertDialogBuilder
            val builder = AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("Pilih jenis gelas")

            // show dialog
            val alertDialog = builder.show()

            // on click change month
            dialogView.findViewById<Button>(R.id.btnChange).setOnClickListener {
                // dismiss dialog
                alertDialog.dismiss()

                // process selected value
                db.MinumanDAO().insert(Minuman(intensitas = this.selectedGlass))

                // Re-render element
                updatePage(view, db)
            }
        }

        // animation
        val btt = AnimationUtils.loadAnimation(activity, R.anim.btt)
        val fluidback = view.findViewById<ImageView>(R.id.fluid_back)
        val fluidfront = view.findViewById<ImageView>(R.id.fluid_front)

        fluidback.startAnimation(btt)
        fluidfront.startAnimation(btt)

        // Inflate the layout for this fragment
        return view
    }

    fun updatePage(view: View, db: asahDatabase){
        // Inisialisasi Progress
        val progress_drink = view.findViewById<com.mikhaellopez.circularprogressbar.CircularProgressBar>(R.id.drink_progressbar)

        val t_intensitas = view.findViewById<TextView>(R.id.air_progresstext)

        var currentIntensitas = 0.0
        val intensitas: List<Minuman> = db.MinumanDAO().getMinuman()
        for(x in intensitas){
            currentIntensitas += x.intensitas.toDouble()
        }
        currentIntensitas /= 1000.0

        //TODO: UPDATE TARGET SESUAI PERHITUNGAN
        var target = 5.0

        t_intensitas.text = "$currentIntensitas/$target"

        // Update Progress
        progress_drink.apply {
            progressMax = 100f
            setProgressWithAnimation(((currentIntensitas*100)/target).toFloat(), 1000)
            startAngle = 180f
        }
    }
}