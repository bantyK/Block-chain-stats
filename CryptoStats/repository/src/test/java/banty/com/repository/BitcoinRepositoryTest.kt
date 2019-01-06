package banty.com.repository

import android.content.Context
import banty.com.datamodels.ContextProvider
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.local.LocalBitcoinRepository
import banty.com.repository.remote.RemoteBitcoinRepository
import banty.com.utility.NetworkConnectivityUtil
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 07/01/19.
 */
class BitcoinRepositoryTest {

    @Mock
    lateinit var localBitcoinRepository: LocalBitcoinRepository

    @Mock
    lateinit var remoteBitcoinRepository: RemoteBitcoinRepository

    @Mock
    lateinit var networkConnectivityUtil: NetworkConnectivityUtil

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var apiResponseModel: Observable<BitcoinApiResponseModel>

    lateinit var bitcoinRepository: BitcoinRepository


    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        bitcoinRepository = BitcoinRepository(localBitcoinRepository, remoteBitcoinRepository, networkConnectivityUtil)
        ContextProvider.setContext(context)
    }

    @Test
    fun shouldGetMarketPriceFromServerIfNetworkIsAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getMarketPrice("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getMarketPrice("timespan")
        verify(remoteBitcoinRepository).getMarketPrice("timespan")
    }

    @Test
    fun shouldGetMarketPriceFromLocalFileIfNetworkIsNotAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(false)
        bitcoinRepository.getMarketPrice("timespan")
        verify(localBitcoinRepository).getMarketPrice("timespan")
        verifyZeroInteractions(remoteBitcoinRepository)
    }

    @Test
    fun shouldSaveMarketPriceDataInLocalFileSystem() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getMarketPrice("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getMarketPrice("timespan")
        verify(localBitcoinRepository).saveMarketPriceModel(apiResponseModel)
    }

    @Test
    fun shouldGetAverageBlockSizeFromServerIfNetworkIsAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getAverageBlockSize("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getAverageBlockSize("timespan")
        verify(remoteBitcoinRepository).getAverageBlockSize("timespan")
    }

    @Test
    fun shouldGetAverageBlockSizeFromLocalFileIfNetworkIsNotAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(false)
        bitcoinRepository.getAverageBlockSize("timespan")
        verify(localBitcoinRepository).getAverageBlockSize("timespan")
        verifyZeroInteractions(remoteBitcoinRepository)
    }

    @Test
    fun shouldSaveAverageBlockSizeDataInLocalFileSystem() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getAverageBlockSize("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getAverageBlockSize("timespan")
        verify(localBitcoinRepository).saveAverageBlockSizeModel(apiResponseModel)
    }

    @Test
    fun shouldGetNumTransactionFromServerIfNetworkIsAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getNumberOfTransactions("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getNumberOfTransactions("timespan")
        verify(remoteBitcoinRepository).getNumberOfTransactions("timespan")
    }

    @Test
    fun shouldGetNumTransactionFromLocalFileIfNetworkIsNotAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(false)
        bitcoinRepository.getNumberOfTransactions("timespan")
        verify(localBitcoinRepository).getNumberOfTransactions("timespan")
        verifyZeroInteractions(remoteBitcoinRepository)
    }

    @Test
    fun shouldSaveNumTransactionDataInLocalFileSystem() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getNumberOfTransactions("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getNumberOfTransactions("timespan")
        verify(localBitcoinRepository).saveNumTransactionModel(apiResponseModel)
    }

    @Test
    fun shouldGetMemorySizePoolFromServerIfNetworkIsAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getMemoryPoolSize("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getMemoryPoolSize("timespan")
        verify(remoteBitcoinRepository).getMemoryPoolSize("timespan")
    }

    @Test
    fun shouldGetMemorySizePoolFromLocalFileIfNetworkIsNotAvailable() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(false)
        bitcoinRepository.getMemoryPoolSize("timespan")
        verify(localBitcoinRepository).getMemoryPoolSize("timespan")
        verifyZeroInteractions(remoteBitcoinRepository)
    }

    @Test
    fun shouldSaveMemorySizePoolDataInLocalFileSystem() {
        `when`(networkConnectivityUtil.isNetworkAvailable()).thenReturn(true)
        `when`(remoteBitcoinRepository.getMemoryPoolSize("timespan")).thenReturn(apiResponseModel)
        bitcoinRepository.getMemoryPoolSize("timespan")
        verify(localBitcoinRepository).saveMemoryPoolSizeModel(apiResponseModel)
    }

}