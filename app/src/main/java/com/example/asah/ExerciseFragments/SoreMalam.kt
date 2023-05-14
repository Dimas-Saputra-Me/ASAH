package com.example.asah.ExerciseFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.asah.R

class SoreMalam : Fragment() {

    private var selectedExercise: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_sore_malam, container, false)

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
                var selected = parent.getItemAtPosition(position)
                // TODO: ini sementara tak kasi string, tapi kayanya lebih gampang jadi int
                // TODO: jadi nanti menyesuiakan aja yang gampang yang mana
                this.selectedExercise = selected.toString()
            }

            //launch
            typeDropDown.setAdapter(arrayAdapter)

            // AlertDialogBuilder
            val builder = AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("Pilih jenis olahraga")

            // show dialog
            val alertDialog = builder.show()

            // on click change month
            dialogView.findViewById<Button>(R.id.btnChange).setOnClickListener {
                // dismiss dialog
                alertDialog.dismiss()

                // process selected value
                // TODO: "nanti ngolah variable namanya selectedExercise"
            }
        }

        // Inflate the layout for this fragment
        return view
    }
}