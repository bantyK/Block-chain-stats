package banty.com.cryptostats.charts

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import banty.com.cryptostats.R
import banty.com.datamodels.response.BitcoinApiResponseModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData

/**
 * Created by Banty on 05/01/19.
 *
 * Fragment to display the chart.
 * All the information required to plot the chart is passed on through the bundle.
 * Uses MPAndroidChart library for displaying the charts : https://github.com/PhilJay/MPAndroidChart
 */
class ChartsFragment : Fragment(), ChartsFragmentMVPContract.View {

    private val logTag = "ChartsFragment"

    private lateinit var lineChart: LineChart

    private var presenter: ChartsFragmentMVPContract.Presenter? = null

    companion object {
        const val PARCEL_KEY = "charts_data"

        /*
        * static function which will be used by the activity to create a new instance of the
        * fragment with the @param BitcoinApiResponseModel passed as bundle.
        * */
        fun newInstance(bitcoinApiResponseModel: BitcoinApiResponseModel): ChartsFragment {
            val args = Bundle()
            args.putParcelable(PARCEL_KEY, bitcoinApiResponseModel)
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

    /**
     * Initializes the presenter
     * */
    private fun initPresenter() {
        presenter = ChartsFragmentPresenter(this)
    }

    /**
     * Intializes the views
     * */
    private fun initViews(view: View) {
        lineChart = view.findViewById(R.id.linechart)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPresenter()
        presenter?.setChartData(getChartsDataFromArguments())
    }

    /**
     * Called by Presenter when chart is ready to be displayed with the the
     * required information
     * */
    override fun showChart(data: LineData) {
        lineChart.data = data
        lineChart.setDescription(presenter?.getDescription(getChartsDataFromArguments()))
    }

    /**
     * Returns the BitcoinApiResponseModel from arguments (set by Activity while creating the newInstance)
     * */
    private fun getChartsDataFromArguments(): BitcoinApiResponseModel? {
        val bitCoinChartsData = arguments?.getParcelable<BitcoinApiResponseModel>(PARCEL_KEY)
        Log.d(logTag, "Response : values size : ${bitCoinChartsData?.values?.size}")
        return bitCoinChartsData
    }
}