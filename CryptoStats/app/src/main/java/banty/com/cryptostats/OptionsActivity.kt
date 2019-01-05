package banty.com.cryptostats

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import banty.com.cryptostats.charts.ChartsFragment
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OptionsActivity : AppCompatActivity(), View.OnClickListener {
    private val tag = "OptionsActivity"
    private val days_30 = "30days"
    private val days_60 = "60days"
    private val days_180 = "180days"
    private val year_1 = "1year"
    private var currentTimeSpan: String = days_30


    @Inject
    lateinit var repository: Repository

    lateinit var optionsFragmentContainer: View
    lateinit var chartFragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        initUIElementes()
        (application as BitcoinStatsApplication).getAppComponent()
            ?.injectDependencies(this)

        if (savedInstanceState == null) {
            getDataFromReository(days_30)
        }
    }

    private fun getDataFromReository(timespan: String) {
        repository.getMemoryPoolSize(timespan = timespan)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                Log.d(tag, "${res.values?.size}")
                showChartsFragment(res)
            }, { error ->
                Log.d(tag, "Error : ${error.message}")
            })
    }

    /*
    * Initializes the UI elements
    * */
    private fun initUIElementes() {
        val button30Days = findViewById<Button>(R.id.button_30days)
        val button60Days = findViewById<Button>(R.id.button_60days)
        val button180Days = findViewById<Button>(R.id.button_180days)
        val button1year = findViewById<Button>(R.id.button_1year)

        button30Days.setOnClickListener(this)
        button60Days.setOnClickListener(this)
        button180Days.setOnClickListener(this)
        button1year.setOnClickListener(this)
    }

    private fun showChartsFragment(res: BitcoinApiResponseModel) {
        Log.d("Banty", "Show Charts Fragment")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ChartsFragment.newInstance(res))
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("data", 1)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_30days -> {
                if (currentTimeSpanIsDifferentThan(days_30)) {
                    getDataFromReository(days_30)
                    currentTimeSpan = days_30
                }
            }
            R.id.button_60days -> {
                if (currentTimeSpanIsDifferentThan(days_60)) {
                    getDataFromReository(days_60)
                    currentTimeSpan = days_60
                }
            }
            R.id.button_180days -> {
                if (currentTimeSpanIsDifferentThan(days_180)) {
                    getDataFromReository(days_180)
                    currentTimeSpan = days_180
                }
            }
            R.id.button_1year -> {
                if (currentTimeSpanIsDifferentThan(year_1)) {
                    getDataFromReository(year_1)
                    currentTimeSpan = year_1
                }
            }
            else -> {
                if (currentTimeSpanIsDifferentThan(days_30)) {
                    getDataFromReository(days_30)
                    currentTimeSpan = days_30
                }
            }
        }
    }

    private fun currentTimeSpanIsDifferentThan(timespan: String): Boolean {
        return !timespan.contentEquals(currentTimeSpan)
    }
}
