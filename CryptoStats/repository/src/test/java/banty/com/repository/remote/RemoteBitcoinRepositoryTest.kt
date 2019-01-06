package banty.com.repository.remote

import banty.com.network.retrofit.BitcoinApiService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 06/01/19.
 */
class RemoteBitcoinRepositoryTest {

    lateinit var remoteBitcoinRepository: RemoteBitcoinRepository

    @Mock
    lateinit var bitcoinApiService: BitcoinApiService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        remoteBitcoinRepository = RemoteBitcoinRepository(bitcoinApiService)
    }

    @Test
    fun nameCallMarketPriceApiWithPassedTimespan() {
        remoteBitcoinRepository.getMarketPrice("timespan")
        verify(bitcoinApiService).getMarketPrice("timespan")
    }

    @Test
    fun nameCallAverageBlockSizeWithPassedTimespan() {
        remoteBitcoinRepository.getAverageBlockSize("timespan")
        verify(bitcoinApiService).getAverageBlockSize("timespan")
    }

    @Test
    fun nameCallNumberOfTransactionsWithPassedTimespan() {
        remoteBitcoinRepository.getNumberOfTransactions("timespan")
        verify(bitcoinApiService).getNumberOfTransactions("timespan")
    }

    @Test
    fun nameCallMemoryPoolSizeApiWithPassedTimespan() {
        remoteBitcoinRepository.getMemoryPoolSize("timespan")
        verify(bitcoinApiService).getMemoryPoolSize("timespan")
    }


}