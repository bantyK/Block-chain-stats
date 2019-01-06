package banty.com.cryptostats.fragments.charts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import banty.com.cryptostats.BitcoinStatsApplication
import banty.com.cryptostats.R
import banty.com.cryptostats.days_180
import banty.com.cryptostats.days_30
import banty.com.cryptostats.days_60
import banty.com.cryptostats.year_1
import banty.com.datamodels.response.BitcoinApiResponseModel
import banty.com.repository.Repository
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Banty on 05/01/19.
 *
 * Fragment to display the chart.
 * All the information required to plot the chart is passed on through the bundle.
 * Uses MPAndroidChart library for displaying the charts : https://github.com/PhilJay/MPAndroidChart
 */
class ChartsFragment : Fragment(), ChartsFragmentMVPContract.View, View.OnClickListener {

    private val logTag = "ChartsFragment"

    private lateinit var progressBar: ProgressBar

    private lateinit var lineChart: LineChart

    private var presenter: ChartsFragmentMVPContract.Presenter? = null

    @Inject
    lateinit var repository: Repository

    private var bitcoinData: BitcoinApiResponseModel? = null

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun updateUI(model: BitcoinApiResponseModel?) {
        hideProgressBar()
    }

    companion object {
        const val PARCEL_KEY = "charts_data"
        const val CHART_OPTION_KEY = "chart_option"
        /*
        * static function which will be used by the activity to create a new instance of the
        * fragment with the @param BitcoinApiResponseModel passed as bundle.
        * */
        fun newInstance(chartOption: String): ChartsFragment {
            val args = Bundle()
            args.putString(CHART_OPTION_KEY, chartOption)
            val chartsFragment = ChartsFragment()
            chartsFragment.arguments = args
            return chartsFragment
        }
    }

    /*
    * Inflates the layout and initialise the views.
    * */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.charts_fragment, container, false)
        initViews(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    /*
    * Inject the repository via dagger 2*/
    private fun initRepository() {
        (activity?.application as BitcoinStatsApplication).getAppComponent()
            ?.injectDependencies(this)
    }

    /**
     * Initialize the presenter
     *
     * The Schedulers required by RxJava in presenter as passed as a dependency so that it can
     * be tested via unit test. See ChartsFragmentPresenterTest for the tests
     * */
    private fun initPresenter() {
        presenter = ChartsFragmentPresenter(
            this,
            ChartCreator(),
            repository,
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
    }

    /**
     * Initialise the views
     * */
    private fun initViews(view: View) {
        lineChart = view.findViewById(R.id.linechart)
        progressBar = view.findViewById(R.id.progress_bar)
        val button30Days = view.findViewById<Button>(R.id.button_30days)
        val button60Days = view.findViewById<Button>(R.id.button_60days)
        val button180Days = view.findViewById<Button>(R.id.button_180days)
        val button1year = view.findViewById<Button>(R.id.button_1year)

        button30Days.setOnClickListener(this)
        button60Days.setOnClickListener(this)
        button180Days.setOnClickListener(this)
        button1year.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRepository()
        initPresenter()
        //saves the response from repository to redraw the graph on Orientation change
        if (savedInstanceState?.get(PARCEL_KEY) != null) {
            Log.d(logTag, "Fragment redrawn, using the saved data")
            showChart(savedInstanceState[PARCEL_KEY] as BitcoinApiResponseModel)
        } else {
            // saved response is not present, make a call to the repository via the presenter
            Log.d(logTag, "New instance of the fragment is created, fetch the new data")
            presenter?.setChart(days_30)
        }
    }

    /**
     * Called by Presenter when chart is ready to be displayed with the the
     * required information
     * */
    override fun showChart(bitcoinApiResponseModel: BitcoinApiResponseModel?) {
        hideProgressBar()
        this.bitcoinData = bitcoinApiResponseModel
        lineChart.data = presenter?.getChartData(bitcoinApiResponseModel)
        lineChart.setDescription(bitcoinApiResponseModel?.description)
        lineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart)
        lineChart.setScaleEnabled(true)
    }

    /*
    * API response from repository is saved in the bundle. This is used to redraw the graph when OS kills and redraw the UI on
    * orientation change. This saves an extra API call which would have been made whenever device orientation changes.
    * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(PARCEL_KEY, bitcoinData)
    }

    /*
    * onClick handler for the time span buttons, changes the graph according to the duration selected by the user
    * */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_30days -> {
                Log.d(logTag, "handle click for 30 days")
                presenter?.handleButtonClick(days_30)
            }
            R.id.button_60days -> {
                Log.d(logTag, "handle click for 60 days")
                presenter?.handleButtonClick(days_60)
            }
            R.id.button_180days -> {
                Log.d(logTag, "handle click for 180 days")
                presenter?.handleButtonClick(days_180)
            }
            R.id.button_1year -> {
                Log.d(logTag, "handle click for 1 year")
                presenter?.handleButtonClick(year_1)
            }
            else -> {
                Log.d(logTag, "default case : 30 days")
                presenter?.handleButtonClick(days_30)
            }
        }
    }

}