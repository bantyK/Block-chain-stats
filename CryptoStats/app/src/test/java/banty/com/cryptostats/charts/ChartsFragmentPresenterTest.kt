package banty.com.cryptostats.charts

import banty.com.cryptostats.charts.utility.convertEpochTimeToDate
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.datamodels.response.Values
import com.github.mikephil.charting.data.LineData
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 05/01/19.
 *
 * Unit test cases for ChartsFragmentPresenter
 */
class ChartsFragmentPresenterTest {

    @Mock
    lateinit var apiResponseModel: BitcoinApiResponseModel

    @Mock
    lateinit var view: ChartsFragmentMVPContract.View

    @Mock
    lateinit var mockChartCreator: ChartCreator

    @Mock
    lateinit var mockLineData: LineData

    lateinit var presenter: ChartsFragmentPresenter

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ChartsFragmentPresenter(view, mockChartCreator)
    }

    private val arrayList: ArrayList<Values>
        get() {
            val values = ArrayList<Values>()
            return values
        }

    fun mockValues(): List<Values> {
        val values = arrayList
        values.add(Values(1234L, 123.4F))
        values.add(Values(2789, 123.4F))
        values.add(Values(1243232L, 123.4F))
        values.add(Values(908637746L, 123.4F))
        return values
    }

    @Test
    fun shouldReturnCorrectValuesForXAxis() {
        `when`(apiResponseModel.values).thenReturn(mockValues())
        val actualValues = presenter.getXAxisValues(apiResponseModel)
        // it should contain the same number of entries as passed
        assertEquals("Size checking", 4, actualValues.size)
        // each entry should be same as result of EpochTimeConverter for that value
        assertEquals("Value checking 1", convertEpochTimeToDate(1234L), actualValues[0])
        assertEquals("Value checking 2", convertEpochTimeToDate(2789L), actualValues[1])
        assertEquals("Value checking 3", convertEpochTimeToDate(1243232L), actualValues[2])
        assertEquals("Value checking 4", convertEpochTimeToDate(908637746L), actualValues[3])
    }

    @Test
    fun shouldReturnCorrectValuesForYAxis() {
        `when`(apiResponseModel.values).thenReturn(mockValues())
        val actualValues = presenter.getYAxisValues(apiResponseModel)
        assertEquals("Size checking", 4, actualValues.size)
    }

    @Test
    fun shouldReturnCorrectDescription() {
        `when`(apiResponseModel.description).thenReturn("Mock Description")
        val description = presenter.getDescription(apiResponseModel)
        assertEquals("Mock Description", description)
    }

    @Test
    fun shouldCreateChartWithGivenXAndYValues() {
        `when`(apiResponseModel.values).thenReturn(mockValues())
        `when`(apiResponseModel.name).thenReturn("name")
        `when`(mockChartCreator.createChart(any(), any(), any())).thenReturn(mockLineData)
        presenter.setChartData(apiResponseModel)

        verify(view).showChart(mockLineData)
    }
}