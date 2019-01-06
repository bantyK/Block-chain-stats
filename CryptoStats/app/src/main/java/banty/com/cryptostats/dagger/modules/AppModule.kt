package banty.com.cryptostats.dagger.modules

import banty.com.cryptostats.fragments.charts.data.BitcoinDataProvider
import banty.com.cryptostats.fragments.charts.data.BitcoinDataProviderImpl
import banty.com.repository.BitcoinRepository
import banty.com.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides the BitcoinRepository
 * */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDataProvider(repository: Repository): BitcoinDataProvider =
        BitcoinDataProviderImpl(repository)

    @Provides
    @Singleton
    fun provideBitcoinRepository(): Repository {
        return BitcoinRepository()
    }
}
