package banty.com.cryptostats.dagger.modules

import android.content.Context
import banty.com.cryptostats.fragments.charts.data.BitcoinDataProvider
import banty.com.cryptostats.fragments.charts.data.BitcoinDataProviderImpl
import banty.com.network.retrofit.BitcoinApiService
import banty.com.network.retrofit.RetrofitClientCreator
import banty.com.repository.BitcoinRepository
import banty.com.repository.Repository
import banty.com.repository.local.LocalBitcoinRepository
import banty.com.repository.local.files.FileManager
import banty.com.repository.remote.RemoteBitcoinRepository
import banty.com.utility.NetworkConnectivityUtil
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Provides the BitcoinRepository
 * */
@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDataProvider(repository: Repository): BitcoinDataProvider =
        BitcoinDataProviderImpl(repository)

    @Provides
    @Singleton
    fun provideBitcoinRepository(
        localBitcoinRepository: LocalBitcoinRepository,
        remoteBitcoinRepository: RemoteBitcoinRepository,
        networkConnectivityUtil: NetworkConnectivityUtil
    ): Repository {
        return BitcoinRepository(localBitcoinRepository, remoteBitcoinRepository, networkConnectivityUtil)
    }

    @Provides
    @Singleton
    fun provideRemoteBitcoinRepository(bitcoinApiService: BitcoinApiService): RemoteBitcoinRepository {
        return RemoteBitcoinRepository(bitcoinApiService)
    }

    @Provides
    @Singleton
    fun provideLocalBitcoinRepository(fileManager: FileManager): LocalBitcoinRepository {
        return LocalBitcoinRepository(fileManager)
    }

    @Provides
    @Singleton
    fun provideFileManager(): FileManager {
        return FileManager()
    }

    @Provides
    fun provideNetworkUtil(): NetworkConnectivityUtil = NetworkConnectivityUtil(context)

    @Provides
    @Singleton
    fun provideBitcoinApiService(retrofitClient: Retrofit): BitcoinApiService {
        return retrofitClient.create(BitcoinApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        return RetrofitClientCreator.createRetrofitClient()
    }
}
