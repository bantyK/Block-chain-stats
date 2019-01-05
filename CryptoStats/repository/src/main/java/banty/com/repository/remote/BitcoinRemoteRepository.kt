package banty.com.repository.remote

import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.network.retrofit.BitcoinApiService
import banty.com.repository.BitcoinRepository
import banty.com.repository.dagger.component.DaggerRepositoryComponent
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Banty on 05/01/19.
 *
 * Implementation of BitcoinRepository to fetch the data from Bitcoin API.
 * Hides the low level implementation of retrofit and api calls.
 */
class BitcoinRemoteRepository : BitcoinRepository {

    init {
        DaggerRepositoryComponent.builder()
            .build().injectRetrofit(this)
    }

    // bitcoin api service injected by Dagger2
    @Inject
    lateinit var bitcoinApiService: BitcoinApiService

    // below are the 4 functions to make the api calls for respective chart data
    override fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel> {
        return bitcoinApiService.getMarketPrice(timespan)
    }

    override fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return bitcoinApiService.getAverageBlockSize(timespan)
    }

    override fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel> {
        return bitcoinApiService.getNumberOfTransactions(timespan)
    }

    override fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return bitcoinApiService.getMemoryPoolSize(timespan)
    }
}