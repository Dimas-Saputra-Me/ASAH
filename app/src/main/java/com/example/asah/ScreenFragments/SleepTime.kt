package com.example.asah.ScreenFragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.asah.R
import com.example.asah.getDate
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import java.util.*

class SleepTime : Fragment() {
    var sleepHour: Int? = null
    var sleepMinute: Int? = null
    var wakeHour: Int? = null
    var wakeMinute: Int? = null

    lateinit var barGraphView: GraphView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep_time, container, false)

        // Update Date
        val t_date = view.findViewById<TextView>(R.id.tanggal_today)
        t_date.text = getDate()

        // btn add sleep
        val btn_add = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btn_add_tidur)
        btn_add.setOnClickListener {
            // inflate dialog
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.add_sleep_dialog, null)

            // AlertDialogBuilder
            val builder = AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setTitle("Isi waktu tidur")

            // show dialog
            val alertDialog = builder.show()

            // set time picker
            // sleep time picker
            val sleepTimePicker = dialogView.findViewById<AutoCompleteTextView>(R.id.textPickSleep)
            sleepTimePicker.apply {
                isFocusable = false // Disable keyboard input
                setOnClickListener {
                    // on below line we are getting the instance of our calendar.
                    val c = Calendar.getInstance()

                    // on below line we are getting our hour, minute.
                    val hour = c.get(Calendar.HOUR_OF_DAY)
                    val minute = c.get(Calendar.MINUTE)

                    val timePickerDialog = TimePickerDialog(context,
                        { view, hourOfDay, minute ->
                            sleepTimePicker.setText("$hourOfDay:$minute")
                            // TODO : nanti ngatur variable ini
                            sleepHour = hourOfDay
                            sleepMinute = minute
                        },
                        hour,
                        minute,
                        true
                    )
                    // at last we are calling show to display our time picker dialog.
                    timePickerDialog.show()
                }
            }

            // wake time picker
            val wakeTimePicker = dialogView.findViewById<AutoCompleteTextView>(R.id.textPickWake)
            wakeTimePicker.apply {
                isFocusable = false // Disable keyboard input
                setOnClickListener {
                    // on below line we are getting the instance of our calendar.
                    val c = Calendar.getInstance()

                    // on below line we are getting our hour, minute.
                    val hour = c.get(Calendar.HOUR_OF_DAY)
                    val minute = c.get(Calendar.MINUTE)

                    val timePickerDialog = TimePickerDialog(context,
                        { view, hourOfDay, minute ->
                            wakeTimePicker.setText("$hourOfDay:$minute")
                            // TODO : nanti ngatur variable ini
                            wakeHour = hourOfDay
                            wakeMinute = minute
                        },
                        hour,
                        minute,
                        true
                    )
                    // at last we are calling show to display our time picker dialog.
                    timePickerDialog.show()
                }
            }

            // on click add sleep time
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