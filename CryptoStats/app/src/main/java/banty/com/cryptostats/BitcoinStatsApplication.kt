package banty.com.cryptostats

import android.app.Application
import banty.com.cryptostats.dagger.component.DaggerAppComponent
import banty.com.cryptostats.dagger.modules.AppModule

/**
 * Created by Banty on 05/01/19.
 * Main Application class
 */
class BitcoinStatsApplication : Application() {

    //app level singleton reference of app component to inject the dependencies in Activities.
    private var appComponent: DaggerAppComponent? = null

    override fun onCreate() {
        super.onCreate()

        // app component is initialised here to make sure that there is a single instance of all the dependencies
        // provided by this component
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .build() as DaggerAppComponent
    }

    // getter function to get the reference of AppComponent
    fun getAppComponent(): DaggerAppComponent? {
        return appComponent
    }
}