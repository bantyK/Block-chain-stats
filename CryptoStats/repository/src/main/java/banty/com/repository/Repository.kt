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

    /*
    * Method to get the MarketPrice from Repository
    * */
    fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel>?

    /*
    * Method to get the AverageBlockSize from Repository
    * */
    fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel>?

    /*
    * Method to get the NumberOfTransactions from Repository
    * */
    fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel>?

    /*
    * Method to get the MemoryPoolSize from Repository
    * */
    fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel>?

    /*
    * Method to save the MarketPrice into Repository
    * */
    fun saveMarketPriceModel(marketPrice: Observable<BitcoinApiResponseModel>)

    /*
    * Method to save the AverageBlockSize Data into Repository
    * */
    fun saveAverageBlockSizeModel(averageBlockSize: Observable<BitcoinApiResponseModel>)

    /*
    * Method to save the NumberOfTransactions data into Repository
    * */
    fun saveNumTransactionModel(numTransactions: Observable<BitcoinApiResponseModel>)

    /*
    * Method to save the MemoryPoolSize data into Repository
    * */
    fun saveMemoryPoolSizeModel(memoryPoolSize: Observable<BitcoinApiResponseModel>)

}