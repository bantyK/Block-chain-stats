package banty.com.cryptostats.fragments.charts

import banty.com.cryptostats.fragments.charts.data.BitcoinDataProvider
import banty.com.cryptostats.utility.convertEpochTimeToDate
import banty.com.datamodels.CHART_MARKET_PRICE
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.datamodels.response.Values
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.lang.Exception

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
    lateinit var dataProvider: BitcoinDataProvider

    lateinit var presenter: ChartsFragmentPresenter

    lateinit var testScheduler: TestScheduler

    @Mock
    lateinit var bitcoinApiResponseModel: BitcoinApiResponseModel

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        `when`(dataProvider.getBitcoinData(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(
            Observable.just(
                bitcoinApiResponseModel
            )
        )
        presenter = ChartsFragmentPresenter(view, mockChartCreator, dataProvider, testScheduler, testScheduler)
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
    fun shouldUpdateViewWhenDataIsAvailableFromRepository() {
        presenter.getDataFromRepository(CHART_MARKET_PRICE, "timespan")
        testScheduler.triggerActions()
        verify(view).showChart(bitcoinApiResponseModel)
    }

    @Test
    fun redrawGraphOnlyIfNewGraphIsDifferentThanCurrentGraph() {
        presenter.handleButtonClick(CHART_MARKET_PRICE, "30days")
        verifyNoMoreInteractions(view)
    }

    @Test
    fun shouldUpdateGraphIfPreviousGraphIsDifferentFromCurrentGraph() {
        presenter.handleButtonClick(CHART_MARKET_PRICE, "some_other_timespan")
        testScheduler.triggerActions()
        verify(view).showProgressBar()
    }

    @Test
    fun shouldSetChartWithCorrectData() {
        presenter.setChart(CHART_MARKET_PRICE, "timespan")
        verify(view).showProgressBar()
        testScheduler.triggerActions()
        verify(view).showChart(ArgumentMatchers.any(BitcoinApiResponseModel::class.java))
    }

    @Test
    fun shouldConstructChartWithCorrectDataFromResponseModel() {
        presenter.getChartData(bitcoinApiResponseModel)
        verify(mockChartCreator).createChart(
            presenter.getXAxisValues(apiResponseModel),
            presenter.getYAxisValues(apiResponseModel),
            bitcoinApiResponseModel.name
        )
    }

    @Test
    fun shouldShowFailedDialogWhenDataFetchFailed() {
        `when`(dataProvider.getBitcoinData(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(Observable.error(Exception("Dummy exception")))
        presenter.getDataFromRepository(CHART_MARKET_PRICE,"timespan")
        testScheduler.triggerActions()
        verify(view).showNetworkError()
    }
}