package banty.com.cryptostats

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import banty.com.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OptionsActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
    }

    override fun onResume() {
        super.onResume()
        (application as BitcoinStatsApplication).getAppComponent()
            ?.injectDependencies(this)

        repository.getAverageBlockSize(timespan = "30days")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { res ->
                Log.d("Banty", res.name)
            }
    }
}
