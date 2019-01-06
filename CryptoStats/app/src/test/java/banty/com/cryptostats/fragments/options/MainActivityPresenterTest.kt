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
    lateinit var view: MainActivityMVPContract.View


    lateinit var mainActivityPresenter: MainActivityPresenter



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivityPresenter = MainActivityPresenter(view)
    }

}