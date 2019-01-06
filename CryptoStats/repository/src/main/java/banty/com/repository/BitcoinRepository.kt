package banty.com.repository

import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.local.LocalBitcoinRepository
import banty.com.repository.remote.RemoteBitcoinRepository
import banty.com.utility.NetworkConnectivityUtil
import io.reactivex.Observable

/**
 * Created by Banty on 05/01/19.
 * High level implementation of Bitcoin repository which provide bitcoin related data
 * uses @param BitcoinRemoteRepository to fetch the data from server and uses @param BitcoinLocalRepository
 * to fetch the data from user's local device storage
 *
 * Checks the network condition of user's device. If the device is connected to internet and fetches and provide the data from
 * server, if not then uses the previously stored data from user's local device.
 *
 * Other caching mechanisms like timestamp, manual refresh etc can also be used to identify on the data source.
 *
 */
class BitcoinRepository(
    private val localBitcoinRepository: LocalBitcoinRepository,
    private val remoteBitcoinRepository: RemoteBitcoinRepository,
    private val networkUtil: NetworkConnectivityUtil
) : Repository {

    /*
    * If device has network connection then fetches the data from server and
    * saves it in local storage using localRepository's save method
    *
    * If network connection is not present than directly fetches the data from local
    * repository
    *
    * Same functionality is implemented by all the other methods which are used to get api data
    * */
    override fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.isNetworkAvailable()) {
            val marketPrice = remoteBitcoinRepository.getMarketPrice(timespan)
            saveMarketPriceModel(marketPrice)
            marketPrice
        } else {
            localBitcoinRepository.getMarketPrice(timespan)
        }
    }


    override fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.isNetworkAvailable()) {
            val averageBlockSize = remoteBitcoinRepository.getAverageBlockSize(timespan)
            localBitcoinRepository.saveAverageBlockSizeModel(averageBlockSize)
            averageBlockSize
        } else {
            localBitcoinRepository.getAverageBlockSize(timespan)
        }
    }

    override fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.isNetworkAvailable()) {
            val numberOfTransactions = remoteBitcoinRepository.getNumberOfTransactions(timespan)
            saveNumTransactionModel(numberOfTransactions)
            numberOfTransactions
        } else {
            localBitcoinRepository.getNumberOfTransactions(timespan)
        }
    }

    override fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.isNetworkAvailable()) {
            val memoryPoolSize = remoteBitcoinRepository.getMemoryPoolSize(timespan)
            saveMemoryPoolSizeModel(memoryPoolSize)
            memoryPoolSize
        } else {
            localBitcoinRepository.getMemoryPoolSize(timespan)
        }
    }

    /*
    * below 4 methods are helper functions which delegate the task to locally storing the
    * data to local repository
    */
    override fun saveMarketPriceModel(marketPrice: Observable<BitcoinApiResponseModel>) {
        localBitcoinRepository.saveMarketPriceModel(marketPrice)
    }

    override fun saveAverageBlockSizeModel(averageBlockSize: Observable<BitcoinApiResponseModel>) {
        localBitcoinRepository.saveAverageBlockSizeModel(averageBlockSize)
    }

    override fun saveNumTransactionModel(numTransactions: Observable<BitcoinApiResponseModel>) {
        localBitcoinRepository.saveNumTransactionModel(numTransactions)
    }

    override fun saveMemoryPoolSizeModel(memoryPoolSize: Observable<BitcoinApiResponseModel>) {
        localBitcoinRepository.saveMemoryPoolSizeModel(memoryPoolSize)
    }
}