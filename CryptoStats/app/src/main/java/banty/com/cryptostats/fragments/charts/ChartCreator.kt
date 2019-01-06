package banty.com.cryptostats.fragments.charts

import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

/**
 * Created by Banty on 05/01/19.
 *
 * A helper class to create the LineData object loaded with information from bitcoin response model to display
 * the chart.
 */
class ChartCreator {
    fun createChart(xValues: List<String>, yValues: List<Entry>, name: String?): LineData {
        val lineDataSet = LineDataSet(yValues, name)
        //Fill the color below the line
        lineDataSet.fillAlpha = 0

        //Set the color of line #039BD2
        lineDataSet.color = Color.rgb(3, 155, 210)

        //Set the line width
        lineDataSet.lineWidth = 2f

        //Set radius of the circles present on the chart line
        lineDataSet.circleRadius = 0.0f

        //Set the text size
        lineDataSet.valueTextSize = 9f

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)

        return LineData(xValues, dataSets)
    }
}