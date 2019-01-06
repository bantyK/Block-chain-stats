package banty.com.cryptostats.options

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
class OptionsActivityPresenterTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var view: OptionsActivityMVPContract.View

    @Mock
    lateinit var bitcoinApiResponseModel: BitcoinApiResponseModel

    lateinit var optionsActivityPresenter: OptionsActivityPresenter

    lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        `when`(repository.getMemoryPoolSize(ArgumentMatchers.anyString())).thenReturn(Observable.just(bitcoinApiResponseModel))
        optionsActivityPresenter = OptionsActivityPresenter(view, repository, testScheduler, testScheduler)
    }

    @Test
    fun shouldUpdateViewWhenDataIsAvailableFromRepository() {
        optionsActivityPresenter.getDataFromRepository("timespan")
        testScheduler.triggerActions()
        verify(view).updateUI(bitcoinApiResponseModel)
    }

    @Test
    fun redrawGraphOnlyIfNewGraphIsDifferentThanCurrentGraph() {
        optionsActivityPresenter.handleButtonClick("30days")
        verifyNoMoreInteractions(view)
    }

    @Test
    fun shouldUpdateGraphIfPreviousGraphIsDifferentFromCurrentGraph() {
        optionsActivityPresenter.handleButtonClick("some_other_timespan")
        testScheduler.triggerActions()
        verify(view).hideChartContainer()
        verify(view).showProgressBar()
    }
}