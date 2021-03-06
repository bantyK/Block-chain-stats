package banty.com.cryptostats.fragments.charts

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import banty.com.cryptostats.BitcoinStatsApplication
import banty.com.cryptostats.R
import banty.com.cryptostats.fragments.charts.data.BitcoinDataProvider
import banty.com.datamodels.*
import banty.com.datamodels.response.BitcoinApiResponseModel
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

    private var progressBar: ProgressBar? = null

    private var lineChart: LineChart? = null

    private var presenter: ChartsFragmentMVPContract.Presenter? = null

    @Inject
    lateinit var dataProvider: BitcoinDataProvider

    private var bitcoinData: BitcoinApiResponseModel? = null

    override fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }

    override fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
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
            dataProvider,
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
    }

    /**
     * Initialise the views
     * */
    private fun initViews(view: View?) {
        lineChart = view?.findViewById<LineChart>(R.id.linechart)
        progressBar = view?.findViewById(R.id.progress_bar)
        val button30Days = view?.findViewById<Button>(R.id.button_30days)
        val button60Days = view?.findViewById<Button>(R.id.button_60days)
        val button180Days = view?.findViewById<Button>(R.id.button_180days)
        val button1year = view?.findViewById<Button>(R.id.button_1year)

        button30Days?.setOnClickListener(this)
        button60Days?.setOnClickListener(this)
        button180Days?.setOnClickListener(this)
        button1year?.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRepository()
        initPresenter()
        //saves the response from repository to redraw the graph on Orientation change
        if (savedInstanceState?.get(PARCEL_KEY) != null) {
            Log.d(logTag, "Fragment redrawn, using the previously saved data")
            showChart(savedInstanceState[PARCEL_KEY] as BitcoinApiResponseModel)
        } else {
            // saved response is not present, make a call to the repository via the presenter
            Log.d(logTag, "New instance of the fragment is created, fetch the new data")
            presenter?.setChart(getChartOptionFromArgument(), days_30)
        }
    }

    /*
    * Get the chart option sent by MainActivity.
    * Returns CHART_MARKET_PRICE by default if no chart value is passed
    * */
    private fun getChartOptionFromArgument(): String {
        return arguments?.getString(CHART_OPTION_KEY) ?: CHART_MARKET_PRICE
    }

    /**
     * Called by Presenter when chart is ready to be displayed with the the
     * required information
     * */
    override fun showChart(bitCoinData: BitcoinApiResponseModel?) {
        hideProgressBar()
        this.bitcoinData = bitCoinData
        lineChart?.data = presenter?.getChartData(bitCoinData)
        lineChart?.setNoDataText(getString(R.string.no_data))
        lineChart?.setDescription(bitCoinData?.description)
        lineChart?.animateX(2500, Easing.EasingOption.EaseInOutQuart)
        lineChart?.setScaleEnabled(true)
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
                presenter?.handleButtonClick(getChartOptionFromArgument(), days_30)
            }
            R.id.button_60days -> {
                Log.d(logTag, "handle click for 60 days")
                presenter?.handleButtonClick(getChartOptionFromArgument(), days_60)
            }
            R.id.button_180days -> {
                Log.d(logTag, "handle click for 180 days")
                presenter?.handleButtonClick(getChartOptionFromArgument(), days_180)
            }
            R.id.button_1year -> {
                Log.d(logTag, "handle click for 1 year")
                presenter?.handleButtonClick(getChartOptionFromArgument(), year_1)
            }
            else -> {
                Log.d(logTag, "default case : 30 days")
                presenter?.handleButtonClick(getChartOptionFromArgument(), days_30)
            }
        }
    }


    /**
     * Show network error message when data fetching is failed.
     *
     * Provide an option to Try again or cancel the dialog
     * */
    override fun showNetworkError() {
        val activityContext: Context? = context
        hideProgressBar()
        if (activityContext != null) {
            val builder = AlertDialog.Builder(activityContext)
            builder.setTitle(getString(R.string.label_data_fetch_failed))
            builder.setMessage(getString(R.string.data_fetch_failed_message))

            val cancelText = getString(R.string.cancel)
            builder.setNegativeButton(cancelText) { dialog, _ ->
                dialog.dismiss()
            }

            val tryAgainText = getString(R.string.try_again)
            builder.setPositiveButton(tryAgainText) { dialog, _ ->
                presenter?.getDataFromRepository(getChartOptionFromArgument(), days_30)
            }
            val dialog = builder.create()
            dialog.show()
        }
    }


}