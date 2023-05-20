package com.example.asah.DrinkFragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.asah.Database.Minuman
import com.example.asah.Database.asahDatabase
import com.example.asah.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import java.util.*


class DrinkGraph : Fragment() {

    lateinit var barGraphView: GraphView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drink_graph, container, false)

        // inisialisasi Database
        val db = Room.databaseBuilder(requireContext(), asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        // Get data
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -7);
            // Get Day Data
        val days: MutableList<Double> = ArrayList();
        days.add(calendar.get(Calendar.DAY_OF_MONTH).toDouble()+1)
        for(i in 1..7){
            days.add(days[i-1]+1)
        }
            // Get Intensitas Data
        val intensitas: MutableList<Double> = ArrayList()
        for(i in 1..7){
            calendar.add(Calendar.DATE, 1);
            val intensitasDB: List<Minuman> = db.MinumanDAO().getMinumanbyDate(date = getDate(calendar))
            intensitas.add(0.0)

            for(x in intensitasDB){
                intensitas[i-1] += x.intensitas.toDouble()
            }

        }

        barGraphView = view.findViewById(R.id.graph)
        val series: BarGraphSeries<DataPoint> = BarGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(days[0], intensitas[0]),
                DataPoint(days[1], intensitas[1]),
                DataPoint(days[2], intensitas[2]),
                DataPoint(days[3], intensitas[3]),
                DataPoint(days[4], intensitas[4]),
                DataPoint(days[5], intensitas[5]),
                DataPoint(days[6], intensitas[6]),

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

        // 7 days layout spacing
        barGraphView.getGridLabelRenderer().setNumHorizontalLabels(7);
        barGraphView.viewport.isScalable = true

        // Update rata-rata
        var avg = 0.0
        for(i in intensitas) avg += i
        avg /= 7
        avg = Math.round(avg * 100.0) / 100.0
        val tx_avg = view.findViewById<TextView>(R.id.textRataRata)
        tx_avg.text = "${avg} ml"

        // Inflate the layout for this fragment
        return view
    }

    fun getDate(c: Calendar) : String {
        val year = c.get(Calendar.YEAR).toString()
        val month = c.get(Calendar.MONTH).toString()
        val day_week = c.get(Calendar.DAY_OF_WEEK)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hari = arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

        val date = "${hari[day_week-1]} - $day/${month.toInt() + 1}/$year"

        return date
    }
}