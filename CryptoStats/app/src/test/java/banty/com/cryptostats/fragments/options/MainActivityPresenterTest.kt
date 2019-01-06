package banty.com.cryptostats.fragments.options

import banty.com.cryptostats.activity.MainActivityMVPContract
import banty.com.cryptostats.activity.MainActivityPresenter
import banty.com.cryptostats.utility.NetworkConnectivityUtil
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 06/01/19.
 */
class MainActivityPresenterTest {

    @Mock
    lateinit var view: MainActivityMVPContract.View

    @Mock
    lateinit var mainActivityPresenter: MainActivityPresenter

    @Mock
    lateinit var networkConnectivityUtil: NetworkConnectivityUtil

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivityPresenter = MainActivityPresenter(view, networkConnectivityUtil)
    }

    @Test
    fun shouldShowNetworkMessageIfNotConnectedWithNetwork() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(false)
        mainActivityPresenter.startUI()
        verify(view).showNoNetworkMessage()
    }

    @Test
    fun shouldAttachFragmentIfConnectedWithNetwork() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        mainActivityPresenter.startUI()
        verify(view).attachFragment()
    }
}