package banty.com.repository.dagger.module

import banty.com.network.retrofit.BitcoinApiService
import banty.com.network.retrofit.RetrofitClientCreator
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
    fun provideBitcoinApiService(retrofitClient: Retrofit): BitcoinApiService {
        return retrofitClient.create(BitcoinApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        return RetrofitClientCreator.createRetrofitClient()
    }
}
