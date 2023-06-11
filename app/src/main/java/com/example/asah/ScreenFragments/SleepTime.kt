package com.example.asah.ScreenFragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.asah.Database.Screen
import com.example.asah.Database.Sleep
import com.example.asah.Database.asahDatabase
import com.example.asah.R
import com.example.asah.getDate
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import java.util.*
import kotlin.math.abs

class SleepTime : Fragment() {
    var sleepHour: Int = 0
    var sleepMinute: Int = 0
    var wakeHour: Int = 0
    var wakeMinute: Int = 0

    lateinit var barGraphView: GraphView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sleep_time, container, false)

        // Inisialisasi Database
        val db = Room.databaseBuilder(requireContext(), asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        // Update Date
        val t_date = view.findViewById<TextView>(R.id.tanggal_today)
        t_date.text = getDate()

        // Inisialisasi Cirular Progress
        updateCircularProgress(view, db)

        // Update text rata rata
        updateRataRata(view, db)

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
                            val formattedMinute = String.format("%02d", minute) // Add leading zero if needed
                            sleepTimePicker.setText("$hourOfDay:$formattedMinute")
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
                            val formattedMinute = String.format("%02d", minute) // Add leading zero if needed
                            wakeTimePicker.setText("$hourOfDay:$formattedMinute")
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

                // Insert to database
                var minuteStart: Int = (sleepHour * 60) + sleepMinute
                var minuteEnd: Int = (wakeHour * 60) + wakeMinute
                var sleepTime: Int = abs(minuteEnd - minuteStart);

                // process selected value
                db.SleepDAO().insert(Sleep(
                    menit = sleepTime,
                    date = getDate()
                ))

                // Reset value
                this.wakeMinute = 0
                this.wakeHour = 0
                this.sleepMinute = 0
                this.sleepHour = 0

                // Update progress circular bar
                updateCircularProgress(view, db)

            }


        }

        // GRAPH
        // Get data
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -7);
        // Get Day Data
        val days: MutableList<Double> = ArrayList();
        days.add(calendar.get(Calendar.DAY_OF_WEEK).toDouble())
        for(i in 1..7){
            days.add((days[i-1]+1))
        }
        // Get Screen Data
        val sleep: MutableList<Double> = ArrayList()
        for(i in 1..7){
            calendar.add(Calendar.DATE, 1);
            val sleepDB: List<Sleep> = db.SleepDAO().getSleepbyDate(date = getDate(calendar))
            sleep.add(0.0)

            for(x in sleepDB){
                sleep[i-1] += x.menit.toDouble()
            }

            // Konversi menit ke jam
            sleep[i-1] = sleep[i-1] / 60.0

        }


        barGraphView = view.findViewById(R.id.graph)
        val series: BarGraphSeries<DataPoint> = BarGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(days[0], sleep[0]),
                DataPoint(days[1], sleep[1]),
                DataPoint(days[2], sleep[2]),
                DataPoint(days[3], sleep[3]),
                DataPoint(days[4], sleep[4]),
                DataPoint(days[5], sleep[5]),
                DataPoint(days[6], sleep[6]),

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

        // days layout spacing
        barGraphView.getGridLabelRenderer().setNumHorizontalLabels(7);
        barGraphView.viewport.isScalable = true

        // custom string label for days
        barGraphView.getGridLabelRenderer().setLabelFormatter(object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    val hari = arrayOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
                    return hari[value.toInt() % 7]
                } else {
                    // show currency for y values
                    super.formatLabel(value, isValueX) + " jam"
                }
            }
        })

        // Inflate the layout for this fragment
        return view
    }

    fun updateCircularProgress(view: View, db: asahDatabase){
        // Inisialisasi Circular Progress
        val progress_screen = view.findViewById<com.mikhaellopez.circularprogressbar.CircularProgressBar>(R.id.sleep_progressbar)

        val t_screen = view.findViewById<TextView>(R.id.sleep_progresstext)

        var currentMenit = 0
        val sleep: List<Sleep> = db.SleepDAO().getSleepbyDate(date = getDate())
        for(x in sleep){
            currentMenit += x.menit
        }

        //TODO: UPDATE TARGET SESUAI PERHITUNGAN
        var target = 8.0 // dalam jam

        // Pisah Hari dan Jam
        val hari = currentMenit / (24 * 60)
        val jam = (currentMenit % (24 * 60)) / 60
        val menit = currentMenit % 60
        t_screen.text = "$hari:$jam:$menit"

        // Update Progress
        Log.d("HU TAOOO", ((currentMenit/60)/target).toFloat().toString())
        progress_screen.apply {
            progressMax = 100f
            setProgressWithAnimation((((currentMenit/60)/target)*100).toFloat(), 1000)
            startAngle = 180f
        }
    }

    fun updateRataRata(view: View, db: asahDatabase){
        var totalSleep: Int = 0;

        val t_ratarata = view.findViewById<TextView>(R.id.sleep_textRataRata)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -7);

        for(i in 1..7){
            calendar.add(Calendar.DATE, 1);
            val sleep: List<Sleep> = db.SleepDAO().getSleepbyDate(date = getDate(calendar))

            for(x in sleep){
                totalSleep += x.menit
            }

        }

        val ratarata = totalSleep.toDouble() / 7.0

        val jam = ratarata / 60
        val menit = ratarata % 60

        t_ratarata.setText("${jam.toInt()} jam ${menit.toInt()} menit")
    }

}