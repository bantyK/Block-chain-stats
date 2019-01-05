package banty.com.repository.local

import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import io.reactivex.Observable

/**
 * Created by Banty on 05/01/19.
 *
 * Local repository for bitcoin data. This module will save the bitcoin related information in
 * user's device instead of fetching it from bitcoin api.
 */

class LocalBitcoinRepository : Repository {
    override fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}