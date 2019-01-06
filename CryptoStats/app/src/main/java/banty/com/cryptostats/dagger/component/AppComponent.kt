package banty.com.cryptostats.dagger.component

import banty.com.cryptostats.activity.MainActivity
import banty.com.cryptostats.dagger.modules.AppModule
import banty.com.cryptostats.fragments.charts.ChartsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Banty on 05/01/19.
 *
 * Interface for dagger to implement which will inject the dependencies to Activities.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectDependencies(target: ChartsFragment)
}