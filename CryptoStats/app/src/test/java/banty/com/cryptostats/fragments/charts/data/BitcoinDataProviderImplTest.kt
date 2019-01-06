package banty.com.cryptostats.fragments.charts.data

import banty.com.datamodels.CHART_AVERAGE_BLOCK_SIZE
import banty.com.datamodels.CHART_MARKET_PRICE
import banty.com.datamodels.CHART_MEMORY_POOL
import banty.com.datamodels.CHART_NUM_TRANSACTION
import banty.com.repository.Repository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Banty on 06/01/19.
 */
class BitcoinDataProviderImplTest {
    @Mock
    lateinit var repository: Repository

    lateinit var bitcoinDataProviderImpl: BitcoinDataProviderImpl

    private val testTimeSpan = "30Days"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        bitcoinDataProviderImpl = BitcoinDataProviderImpl(repository)
    }

    @Test
    fun shouldCallMarketApiWhenChartOptionIsMarketPrice() {
        bitcoinDataProviderImpl.getBitcoinData(CHART_MARKET_PRICE, testTimeSpan)
        verify(repository).getMarketPrice(testTimeSpan)
    }

    @Test
    fun should_Call_AverageBlockSizeApi_When_ChartOption_Is_AverageBlock() {
        bitcoinDataProviderImpl.getBitcoinData(CHART_AVERAGE_BLOCK_SIZE, testTimeSpan)
        verify(repository).getAverageBlockSize(testTimeSpan)
    }

    @Test
    fun should_Call_NumTransaction_When_ChartOption_Is_NumTransaction() {
        bitcoinDataProviderImpl.getBitcoinData(CHART_NUM_TRANSACTION, testTimeSpan)
        verify(repository).getNumberOfTransactions(testTimeSpan)
    }

    @Test
    fun should_Call_MemoryPool_When_ChartOption_Is_MemPool() {
        bitcoinDataProviderImpl.getBitcoinData(CHART_MEMORY_POOL, testTimeSpan)
        verify(repository).getMemoryPoolSize(testTimeSpan)
    }
}