package com.example.asah.ScreenFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.asah.R

class ScreenTime : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen_time, container, false)

        // btn add sleep
        val btn_add = view.findViewById<ImageView>(R.id.btn_add_pemakaian)
        btn_add.setOnClickListener {
            // inflate dialog
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.add_screen_dialog, null)

            // AlertDialogBuilder
            val builder = AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("Isi batas pemakaian")

            // show dialog
            val alertDialog = builder.show()

            // respond to slider
            val slider = dialogView.findViewById<com.google.android.material.slider.Slider>(R.id.add_batas)
            slider.addOnChangeListener { slider, value, fromUser ->
                // Responds to when slider's value is changed
                // TODO : wkwkwkwkw, maap nambah kerjaan
            }

            // on click add screen time
            dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {
                // dismiss dialog
                alertDialog.dismiss()

                // process selected value
                // TODO : Haloo, todo disini
            }
        }


        // Inflate the layout for this fragment
        return view
    }
}