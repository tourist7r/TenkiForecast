package com.nocakenocode.tenkiforecast.renderers

import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*


/**
 * This is a class that is used to format the x values
 * The implementation below will reformat the added values
 * which are the day of the year into a short date format
 * for the chart.
 *
 * Created by Fahad on 2018-03-03.
 */

class CustomXAxisValueFormatter : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase): String {

        // used to format the date
        var sdf = SimpleDateFormat("MMM d")
        // get day of year
        val dayOfYear = value.toInt()
        // use current calendar to convert the given day into time
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear)
        var result:Date = calendar.time

        return "" + sdf.format(result) //format and return result as given pattern in sdf
    }

}