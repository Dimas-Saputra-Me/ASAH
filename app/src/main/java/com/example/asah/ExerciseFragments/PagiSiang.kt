package com.example.asah.ExerciseFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.asah.Database.Minuman
import com.example.asah.Database.Olahraga
import com.example.asah.Database.asahDatabase
import com.example.asah.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class PagiSiang : Fragment() {

    private var selectedExercise: Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_pagi_siang, container, false)

        // inisialisasi Database
        val db = Room.databaseBuilder(requireContext(), asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        // btn add exercise
        val btn_add = view.findViewById<ImageView>(R.id.btn_add_olahraga)
        btn_add.setOnClickListener {
            // inflate dialog
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.add_exercise_dialog, null)

            // set drop down
            val items = listOf(
                "Jogging (? kcal/jam)",
                "Sepeda (? kcal/jam)",
                "Gym(? kcal/jam)"
            )
            val arrayAdapter = ArrayAdapter(
                requireActivity(),
                R.layout.list_dropdown,
                items
            )
            val typeDropDown: AutoCompleteTextView = dialogView.findViewById<AutoCompleteTextView>(R.id.add_exercise)

            typeDropDown.setOnItemClickListener { parent, view, position, id ->
                // TODO: change cal value
                val positionToCal: Array<Double> = arrayOf(1.0, 2.0, 3.0)
                this.selectedExercise = positionToCal[position]
            }

            //launch
            typeDropDown.setAdapter(arrayAdapter)

            // AlertDialogBuilder
            val builder = AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("Pilih jenis olahraga")

            // show dialog
            val alertDialog = builder.show()

            // on click change exercise
            dialogView.findViewById<Button>(R.id.btnChange).setOnClickListener {
                // dismiss dialog
                alertDialog.dismiss()

                // get durasi value
                val durasi = dialogView.findViewById<TextInputLayout>(R.id.layout_input_durasi).editText?.text.toString().toDouble()

                // process selected value
                if(this.selectedExercise != null){
                    db.OlahragaDAO().insert(Olahraga(
                        waktu = false,
                        kalori = this.selectedExercise!!,
                        durasi = durasi,
                        date = getDate()
                    ))
                }
                this.selectedExercise = null

                // TODO: re-render graph
            }
        }

        // Inflate the layout for this fragment
        return view
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