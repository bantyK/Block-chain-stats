package banty.com.repository.dagger.module

import banty.com.network.retrofit.BitcoinApiService
import banty.com.network.retrofit.RetrofitClientCreator
import banty.com.repository.local.LocalBitcoinRepository
import banty.com.repository.remote.RemoteBitcoinRepository
import banty.com.repository.utility.NetworkUtil
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
    fun provideLocalBitcoinRepository(): LocalBitcoinRepository {
        return LocalBitcoinRepository()
    }

    @Provides
    fun provideNetworkUtil(): NetworkUtil = NetworkUtil()

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
