package banty.com.cryptostats.fragments.charts.data

import banty.com.datamodels.CHART_AVERAGE_BLOCK_SIZE
import banty.com.datamodels.CHART_MARKET_PRICE
import banty.com.datamodels.CHART_MEMORY_POOL
import banty.com.datamodels.CHART_NUM_TRANSACTION
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import io.reactivex.Observable

/**
 * Created by Banty on 06/01/19.
 *
 * Implementation of BitcoinDataProvider.
 *
 * Calls one of the 4 methods exposed by the Repository based on the parameters.
 *
 * Fragments are not aware of how the data is fetched, thus this allows the underlying
 * data fetching implementation to change without changing anything in the UI.
 */
class BitcoinDataProviderImpl(val repository: Repository) : BitcoinDataProvider {

    override fun getBitcoinData(option: String, timespan: String): Observable<BitcoinApiResponseModel> {
        return when (option) {
            CHART_MARKET_PRICE -> repository.getMarketPrice(timespan)
            CHART_AVERAGE_BLOCK_SIZE -> repository.getAverageBlockSize(timespan)
            CHART_NUM_TRANSACTION -> repository.getNumberOfTransactions(timespan)
            CHART_MEMORY_POOL -> repository.getMemoryPoolSize(timespan)
            else -> repository.getMarketPrice(timespan)
        }
    }

}