package banty.com.repository

import banty.com.datamodels.response.BitcoinApiResponseModel
import io.reactivex.Observable

/**
 * Created by Banty on 05/01/19.
 *
 * Uses Repository pattern for providing the data objects
 * Hides the details of data retrieval mechanism. Specified 4 functions will be exposed to clients.
 * Interface which will be exposed to caller modules for fetching the bitcoin related data.
 */
interface Repository {

    fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel>

    fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel>

    fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel>

    fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel>
}