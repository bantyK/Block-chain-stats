package banty.com.repository

import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.dagger.component.DaggerRepositoryComponent
import banty.com.repository.dagger.module.RepositoryModule
import banty.com.repository.local.LocalBitcoinRepository
import banty.com.repository.remote.RemoteBitcoinRepository
import banty.com.repository.utility.NetworkUtil
import io.reactivex.Observable
import javax.inject.Inject

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
class BitcoinRepository : Repository {

    @Inject
    lateinit var remoteBitcoinRepository: RemoteBitcoinRepository

    @Inject
    lateinit var localBitcoinRepository: LocalBitcoinRepository

    @Inject
    lateinit var networkUtil: NetworkUtil

    init {
        DaggerRepositoryComponent.builder()
            .repositoryModule(RepositoryModule())
            .build()
            .injectRetrofit(this)
    }

    override fun getMarketPrice(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.deviceConnectedToNetwork()) {
            remoteBitcoinRepository.getMarketPrice(timespan)
        } else {
            localBitcoinRepository.getMarketPrice(timespan)
        }
    }

    override fun getAverageBlockSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.deviceConnectedToNetwork()) {
            remoteBitcoinRepository.getAverageBlockSize(timespan)
        } else {
            localBitcoinRepository.getAverageBlockSize(timespan)
        }
    }

    override fun getNumberOfTransactions(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.deviceConnectedToNetwork()) {
            remoteBitcoinRepository.getNumberOfTransactions(timespan)
        } else {
            localBitcoinRepository.getNumberOfTransactions(timespan)
        }
    }

    override fun getMemoryPoolSize(timespan: String): Observable<BitcoinApiResponseModel> {
        return if (networkUtil.deviceConnectedToNetwork()) {
            remoteBitcoinRepository.getMemoryPoolSize(timespan)
        } else {
            localBitcoinRepository.getMemoryPoolSize(timespan)
        }
    }
}