package com.example.asah.ExerciseFragments

import android.graphics.Color
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
import androidx.room.Room
import com.example.asah.Database.Olahraga
import com.example.asah.Database.asahDatabase
import com.example.asah.R
import com.google.android.material.textfield.TextInputLayout
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import java.util.*

class SoreMalam : Fragment() {

    private var selectedExercise: Double? = null
    lateinit var barGraphView: GraphView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_sore_malam, container, false)

        // inisialisasi Database
        val db = Room.databaseBuilder(requireContext(), asahDatabase::class.java, "asah-db").allowMainThreadQueries().build()

        // Get data
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -7);
        // Get Day Data
        val days: MutableList<Double> = ArrayList();
        days.add(calendar.get(Calendar.DAY_OF_WEEK).toDouble())
        for(i in 1..7){
            days.add(days[i-1]+1)
        }
        // Get Intensitas Data
        val intensitas: MutableList<Double> = ArrayList()
        for(i in 1..7){
            calendar.add(Calendar.DATE, 1);
            val olahragaDB: List<Olahraga> = db.OlahragaDAO().getOlahragabyDateandWaktu(date = getDate(calendar), waktu = true)
            intensitas.add(0.0)

            for(x in olahragaDB){
                intensitas[i-1] += (x.kalori * x.durasi)
            }

        }

        // edit graph
        // TODO : nanti edit graph nya disini ahay
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

        // days layout spacing
        barGraphView.getGridLabelRenderer().setNumHorizontalLabels(7);
        barGraphView.viewport.isScalable = true

        // custom string label for days
        barGraphView.getGridLabelRenderer().setLabelFormatter(object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    val hari = arrayOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
                    return hari[value.toInt()-2]
                } else {
                    // show currency for y values
                    super.formatLabel(value, isValueX) + " kcal"
                }
            }
        })

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

            // on click change month
            dialogView.findViewById<Button>(R.id.btnChange).setOnClickListener {
                // dismiss dialog
                alertDialog.dismiss()

                // get durasi value
                val durasi = dialogView.findViewById<TextInputLayout>(R.id.layout_input_durasi).editText?.text.toString().toDouble()

                // process selected value
                if(this.selectedExercise != null){
                    db.OlahragaDAO().insert(
                        Olahraga(
                        waktu = true,
                        kalori = this.selectedExercise!!,
                        durasi = durasi,
                        date = getDate()
                    )
                    )
                }
                this.selectedExercise = null

                // TODO: re-render graph
            }
        }

        // TODO: Streak harian
        // TODO: Rata-Rata Kalori

        // Inflate the layout for this fragment
        return view
    }

    fun getDate(c: Calendar = Calendar.getInstance()) : String {
        val year = c.get(Calendar.YEAR).toString()
        val month = c.get(Calendar.MONTH).toString()
        val day_week = c.get(Calendar.DAY_OF_WEEK)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hari = arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

        val date = "${hari[day_week-1]} - $day/${month.toInt() + 1}/$year"

        return date
    }
}