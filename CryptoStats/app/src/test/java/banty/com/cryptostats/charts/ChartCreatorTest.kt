package banty.com.cryptostats.charts

import com.github.mikephil.charting.data.Entry
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

    fun getX(): List<String> {
        return Arrays.asList("1234")
    }

    fun getY(): List<Entry> {
        return Arrays.asList(Entry(1234F, 0))
    }
}