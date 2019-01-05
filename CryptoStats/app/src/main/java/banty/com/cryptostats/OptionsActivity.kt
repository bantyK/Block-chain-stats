package banty.com.cryptostats

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import banty.com.cryptostats.charts.ChartsFragment
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OptionsActivity : AppCompatActivity() {

    private val tag = OptionsActivity::class.java.canonicalName

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        (application as BitcoinStatsApplication).getAppComponent()
            ?.injectDependencies(this)

        if (savedInstanceState == null) {
            repository.getMemoryPoolSize(timespan = "30days")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    Log.d(tag, "${res.values?.size}")
                    showChartsFragment(res)
                }, { error ->
                    Log.d(tag, "Error : ${error.message}")
                })
        }
    }

    private fun showChartsFragment(res: BitcoinApiResponseModel) {
        Log.d("Banty", "Show Charts Fragment")
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ChartsFragment.newInstance(res))
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("data", 1)
    }
}
