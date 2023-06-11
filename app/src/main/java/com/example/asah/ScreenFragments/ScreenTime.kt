package com.example.asah.ScreenFragments

import android.app.usage.UsageStatsManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.asah.Database.Minuman
import com.example.asah.Database.Screen
import com.example.asah.Database.asahDatabase
import com.example.asah.R
import com.example.asah.getDate
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import java.util.*

class ScreenTime : Fragment() {

    lateinit var barGraphView: GraphView
    var selectedTime: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen_time, container, false)

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
                this.selectedTime = value.toInt()
            }

            // on click add screen time
            dialogView.findViewById<Button>(R.id.btnAdd).setOnClickListener {
                // dismiss dialog
                alertDialog.dismiss()

                // process selected value
                if(this.selectedTime != null){
                    db.ScreenDAO().insert(Screen(jam = this.selectedTime!!, date = getDate()))
                }
                this.selectedTime = null

                // Re-render Progress Circular
                updateCircularProgress(view, db)

                // TODO : Re-render graph
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
        val screen: MutableList<Double> = ArrayList()
        for(i in 1..7){
            calendar.add(Calendar.DATE, 1);
            val screenDB: List<Screen> = db.ScreenDAO().getScreenbyDate(date = getDate(calendar))
            screen.add(0.0)

            for(x in screenDB){
                screen[i-1] += x.jam.toDouble()
            }

        }


        barGraphView = view.findViewById(R.id.graph)
        val series: BarGraphSeries<DataPoint> = BarGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(days[0], screen[0]),
                DataPoint(days[1], screen[1]),
                DataPoint(days[2], screen[2]),
                DataPoint(days[3], screen[3]),
                DataPoint(days[4], screen[4]),
                DataPoint(days[5], screen[5]),
                DataPoint(days[6], screen[6]),

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
        val progress_screen = view.findViewById<com.mikhaellopez.circularprogressbar.CircularProgressBar>(R.id.screen_progressbar)

        val t_screen = view.findViewById<TextView>(R.id.screen_progresstext)

        var currentJam = 0.0
        val screen: List<Screen> = db.ScreenDAO().getScreenbyDate(date = getDate())
        for(x in screen){
            currentJam += x.jam.toDouble()
        }

        //TODO: UPDATE TARGET SESUAI PERHITUNGAN
        var target = 24.0

        // Pisah Hari dan Jam
        var hari = currentJam.toInt() / 24
        var jam = if((currentJam.toInt() % 24) == 0) "00" else (currentJam.toInt() % 24)
        var menit = "00"
        t_screen.text = "$hari:$jam:$menit"

        // Update Progress
        progress_screen.apply {
            progressMax = 100f
            setProgressWithAnimation(((currentJam*100)/target).toFloat(), 1000)
            startAngle = 180f
        }
    }

    fun updateRataRata(view: View, db: asahDatabase){
        var totalScreen: Int = 0;

        val t_ratarata = view.findViewById<TextView>(R.id.screen_textRataRata)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -7);

        for(i in 1..7){
            calendar.add(Calendar.DATE, 1);
            val screen: List<Screen> = db.ScreenDAO().getScreenbyDate(date = getDate(calendar))

            for(x in screen){
               totalScreen += x.jam
            }

        }

        val ratarata = String.format("%.2f", totalScreen.toDouble() / 7.0)

        t_ratarata.setText("$ratarata jam 0 menit")

    }

}