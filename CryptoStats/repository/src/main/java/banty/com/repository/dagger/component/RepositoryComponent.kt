package banty.com.repository.dagger.component

import banty.com.repository.BitcoinRepository
import banty.com.repository.dagger.module.RepositoryModule
import banty.com.repository.remote.RemoteBitcoinRepository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Banty on 05/01/19.
 *
 * Interface which will be implemented by Dagger to inject dependencies into the Repository classes.
 */
@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {
    fun injectRetrofit(bitcoinRepository: BitcoinRepository)
}