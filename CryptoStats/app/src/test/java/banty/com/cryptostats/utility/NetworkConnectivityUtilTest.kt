package banty.com.cryptostats.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import banty.com.utility.NetworkConnectivityUtil
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 06/01/19.
 */
class NetworkConnectivityUtilTest {

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Mock
    lateinit var activeNetworkInfo: NetworkInfo

    lateinit var networkConnectivityUtil: NetworkConnectivityUtil

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(context.getSystemService(ArgumentMatchers.anyString())).thenReturn(connectivityManager)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(activeNetworkInfo)
        networkConnectivityUtil = NetworkConnectivityUtil(context)
    }

    @Test
    fun shouldReturnTrueIfConnectedToNetwork() {
        `when`(activeNetworkInfo.isConnected).thenReturn(true)
        assertTrue(networkConnectivityUtil.isNetworkAvailable())
    }

    @Test
    fun shouldReturnFalseIfNotConnectedToNetwork() {
        `when`(activeNetworkInfo.isConnected).thenReturn(false)
        assertFalse(networkConnectivityUtil.isNetworkAvailable())
    }
}