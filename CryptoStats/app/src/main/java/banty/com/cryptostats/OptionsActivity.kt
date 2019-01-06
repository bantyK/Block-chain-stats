package banty.com.cryptostats

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import banty.com.cryptostats.charts.ChartsFragment
import banty.com.cryptostats.options.*
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class OptionsActivity : AppCompatActivity(), View.OnClickListener, OptionsActivityMVPContract.View {
    override fun updateUI(model: BitcoinApiResponseModel?) {
        showChartsFragment(model)
        hideProgressBar()
        displayChartContainer()
    }

    private val logTag = "OptionsActivity"

    private lateinit var presenter: OptionsActivityMVPContract.Presenter

    private lateinit var optionsFragmentContainer: View
    private lateinit var chartFragmentContainer: View
    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        initUIElementes()

        (application as BitcoinStatsApplication).getAppComponent()
            ?.injectDependencies(this)

        presenter = OptionsActivityPresenter(this, repository, Schedulers.io(), AndroidSchedulers.mainThread())

        if (savedInstanceState == null) {
            showProgressBar()
            presenter.getDataFromRepository(days_30)
        }
    }

    /*
    * Initializes the UI elements
    * */
    private fun initUIElementes() {
        chartFragmentContainer = findViewById(R.id.charts_fragment_container)
        optionsFragmentContainer = findViewById(R.id.options_fragment_container)
        progressBar = findViewById(R.id.progress_bar)

        val button30Days = findViewById<Button>(R.id.button_30days)
        val button60Days = findViewById<Button>(R.id.button_60days)
        val button180Days = findViewById<Button>(R.id.button_180days)
        val button1year = findViewById<Button>(R.id.button_1year)

        button30Days.setOnClickListener(this)
        button60Days.setOnClickListener(this)
        button180Days.setOnClickListener(this)
        button1year.setOnClickListener(this)
    }

    override fun showChartsFragment(res: BitcoinApiResponseModel?) {
        Log.d(logTag, "Show Charts Fragment")
        supportFragmentManager.beginTransaction()
            .replace(R.id.charts_fragment_container, ChartsFragment.newInstance(res))
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("data", 1)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_30days -> {
                Log.d(logTag, "handle click for 30 days")
                presenter.handleButtonClick(days_30)
            }
            R.id.button_60days -> {
                Log.d(logTag, "handle click for 60 days")
                presenter.handleButtonClick(days_60)
            }
            R.id.button_180days -> {
                Log.d(logTag, "handle click for 180 days")
                presenter.handleButtonClick(days_180)
            }
            R.id.button_1year -> {
                Log.d(logTag, "handle click for 1 year")
                presenter.handleButtonClick(year_1)
            }
            else -> {
                Log.d(logTag, "default case : 30 days")
                presenter.handleButtonClick(days_30)
            }
        }
    }

    override fun hideChartContainer() {
        chartFragmentContainer.visibility = View.GONE
    }

    override fun displayChartContainer() {
        chartFragmentContainer.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
}
