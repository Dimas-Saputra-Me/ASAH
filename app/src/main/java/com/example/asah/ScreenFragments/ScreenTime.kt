package com.example.asah.ScreenFragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.asah.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

class ScreenTime : Fragment() {

    lateinit var barGraphView: GraphView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen_time, container, false)

        // btn add sleep
        val btn_add = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_add_pemakaian)
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

        // bargraphview
        barGraphView = view.findViewById(R.id.graph)
        val series: BarGraphSeries<DataPoint> = BarGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.


            )
        )

        // Styling
        series.setValueDependentColor { data ->
            Color.GREEN
        }

        series.spacing = 10

        // draw values on top
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.GREEN

        series.color = R.color.green
        barGraphView.addSeries(series)

        // Inflate the layout for this fragment
        return view
    }
}