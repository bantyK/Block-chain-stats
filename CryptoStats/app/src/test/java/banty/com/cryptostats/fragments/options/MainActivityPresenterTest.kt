package banty.com.cryptostats.fragments.options

import banty.com.cryptostats.activity.MainActivityMVPContract
import banty.com.cryptostats.activity.MainActivityPresenter
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 06/01/19.
 */
class MainActivityPresenterTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var view: MainActivityMVPContract.View

    @Mock
    lateinit var bitcoinApiResponseModel: BitcoinApiResponseModel

    lateinit var mainActivityPresenter: MainActivityPresenter

    lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        `when`(repository.getMemoryPoolSize(ArgumentMatchers.anyString())).thenReturn(Observable.just(bitcoinApiResponseModel))
        mainActivityPresenter =
                MainActivityPresenter(view, repository, testScheduler, testScheduler)
    }

    @Test
    fun shouldUpdateViewWhenDataIsAvailableFromRepository() {
        mainActivityPresenter.getDataFromRepository("timespan")
        testScheduler.triggerActions()
        verify(view).updateUI(bitcoinApiResponseModel)
    }

    @Test
    fun redrawGraphOnlyIfNewGraphIsDifferentThanCurrentGraph() {
        mainActivityPresenter.handleButtonClick("30days")
        verifyNoMoreInteractions(view)
    }

    @Test
    fun shouldUpdateGraphIfPreviousGraphIsDifferentFromCurrentGraph() {
        mainActivityPresenter.handleButtonClick("some_other_timespan")
        testScheduler.triggerActions()
        verify(view).hideChartContainer()
        verify(view).showProgressBar()
    }
}