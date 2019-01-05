package banty.com.repository.local

import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import io.reactivex.Observable

/**
 * Created by Banty on 05/01/19.
 *
 * Added this class only to show the usage of repository pattern with both local
 * and remote data sources. Local data storage is not implemented as part of this project.
 *
 * Local repository for bitcoin data. This module will save the bitcoin related information in
 * user's device instead of fetching it from bitcoin api.
 */

class LocalBitcoinRepository : Repository {
    override fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel> {
        //local storage is not implemented as a part of this project. I'm only using remote data source.
        // This class is added only for completely showing the usage of repository pattern.
        TODO("not implemented")
    }

    override fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel> {
        TODO("not implemented")
    }

    override fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel> {
        TODO("not implemented")
    }

    override fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel> {
        TODO("not implemented")
    }
}