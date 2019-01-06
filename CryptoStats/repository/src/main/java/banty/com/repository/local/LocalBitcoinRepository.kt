package banty.com.repository.local

import banty.com.datamodels.CHART_AVERAGE_BLOCK_SIZE
import banty.com.datamodels.CHART_MARKET_PRICE
import banty.com.datamodels.CHART_MEMORY_POOL
import banty.com.datamodels.CHART_NUM_TRANSACTION
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import banty.com.repository.local.files.FileManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Banty on 05/01/19.
 *
 * Device's internal file system is used as a Local data storage.
 * Database, External storage etc also be used
 *
 * Local repository for bitcoin data. This module will save the bitcoin related information in
 * user's device instead of fetching it from bitcoin api.
 */

class LocalBitcoinRepository(val fileManager: FileManager) : Repository {

    // below 4 methods uses the fileManager class to get the data from the local file system
    override fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return Observable.just(fileManager.getObjectFromFile(CHART_AVERAGE_BLOCK_SIZE))

    }

    override fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel> {
        return Observable.just(fileManager.getObjectFromFile(CHART_NUM_TRANSACTION))
    }

    override fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return Observable.just(fileManager.getObjectFromFile(CHART_MEMORY_POOL))
    }

    override fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel> {
        return Observable.just(fileManager.getObjectFromFile(CHART_MARKET_PRICE))
    }

    // below 4 methods saves the data in the internal file system
    override fun saveMarketPriceModel(marketPrice: Observable<BitcoinApiResponseModel>) {
        saveModel(CHART_MARKET_PRICE, marketPrice)
    }

    override fun saveAverageBlockSizeModel(averageBlockSize: Observable<BitcoinApiResponseModel>) {
        saveModel(CHART_AVERAGE_BLOCK_SIZE, averageBlockSize)
    }

    override fun saveNumTransactionModel(numTransactions: Observable<BitcoinApiResponseModel>) {
        saveModel(CHART_NUM_TRANSACTION, numTransactions)
    }

    override fun saveMemoryPoolSizeModel(memoryPoolSize: Observable<BitcoinApiResponseModel>) {
        saveModel(CHART_MEMORY_POOL, memoryPoolSize)
    }

    /*
    * Uses RxJava to delegate the file writing operation to IO thread,
    * keeping the UI thread free
    * */
    private fun saveModel(chartType: String, observable: Observable<BitcoinApiResponseModel>) {
        observable
            .subscribeOn(Schedulers.io())
            .subscribe {
                writeFileOnInternalStorage(chartType, it)
            }
    }

    /*
    * Uses file manager to save data on file
    * */
    private fun writeFileOnInternalStorage(chartType: String, model: BitcoinApiResponseModel) {
        fileManager.saveObjectToFile(chartType, model)
    }
}