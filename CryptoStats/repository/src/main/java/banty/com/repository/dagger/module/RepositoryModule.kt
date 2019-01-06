package banty.com.repository.dagger.module

import android.content.Context
import banty.com.datamodels.ContextProvider
import banty.com.network.retrofit.BitcoinApiService
import banty.com.network.retrofit.RetrofitClientCreator
import banty.com.repository.local.LocalBitcoinRepository
import banty.com.repository.local.files.FileManager
import banty.com.repository.remote.RemoteBitcoinRepository
import banty.com.utility.NetworkConnectivityUtil
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module class to provide the retrofit dependency to the BitcoinRemoteRepository.
 * */
@Module
class RepositoryModule {

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
    fun provideNetworkUtil(context: Context): NetworkConnectivityUtil = NetworkConnectivityUtil(context)


    @Provides
    @Singleton
    fun provideContext(): Context {
        return ContextProvider.getContext()
    }

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
