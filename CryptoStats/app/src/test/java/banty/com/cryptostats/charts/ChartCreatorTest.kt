package banty.com.cryptostats.charts

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.Utils
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by Banty on 05/01/19.
 */
class ChartCreatorTest {
    val chartCreator = ChartCreator()

    @Test
    fun shouldReturnChartWithSpecifiedParameters() {
        val lineData = chartCreator.createChart(getX(), getY(), "name")
        assertEquals(1, lineData.dataSetCount)
    }

    @Test
    fun shouldSetAlphaTo0() {
        val lineData = chartCreator.createChart(getX(), getY(), "name")
        assertEquals(0,lineData.dataSets[0].fillAlpha)
    }

    @Test
    fun shouldSetLineWidthTo2() {
        val lineData = chartCreator.createChart(getX(), getY(), "name")
        assertEquals(2,lineData.dataSets[0].lineWidth.toInt())
    }

    @Test
    fun shouldSetCircleRadiusTo0() {
        val lineData = chartCreator.createChart(getX(), getY(), "name")
        assertEquals(0,lineData.dataSets[0].circleRadius.toInt())
    }

    @Test
    fun shouldSetTextSizeValueTo9() {
        val lineData = chartCreator.createChart(getX(), getY(), "name")
        assertEquals(9,lineData.dataSets[0].valueTextSize.toInt())
    }


    fun getX(): List<String> {
        return Arrays.asList("1234")
    }

    fun getY(): List<Entry> {
        return Arrays.asList(Entry(1234F, 0))
    }
}