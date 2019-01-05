package banty.com.cryptostats.charts

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import banty.com.cryptostats.R
import banty.com.datamodels.response.BitcoinApiResponseModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

/**
 * Created by Banty on 05/01/19.
 *
 * Fragment to display the chart.
 * All the information required to plot the chart is passed on bundle.
 * Uses MPAndroidChart library for displaying the charts : https://github.com/PhilJay/MPAndroidChart
 */
class ChartsFragment : Fragment() {

    lateinit var lineChart: LineChart

    companion object {
        val PARCEL_KEY = "charts_data"

        fun newInstance(bitcoinApiResponseModel: BitcoinApiResponseModel): ChartsFragment {
            val args = Bundle()
            args.putParcelable(PARCEL_KEY, bitcoinApiResponseModel)
            val chartsFragment = ChartsFragment()
            chartsFragment.arguments = args
            return chartsFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.charts_fragment, container, false)
        lineChart = view.findViewById(R.id.linechart)
        setChartData(lineChart)
        return view
    }

    private fun setChartData(lineChart: LineChart?) {
        val xVals = setXAxisValues()
        val yVals = getYAxisValues()

        val set1 = LineDataSet(yVals, "My Chart")
        set1.fillAlpha = 110
        set1.color = Color.BLACK
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 9f
        set1.setDrawFilled(true)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(xVals, dataSets)
        lineChart?.data = data

    }

    private fun setXAxisValues(): ArrayList<String> {
        val xVals = ArrayList<String>()
        val bitCoinData = getChartsDataFromArguments()
        val values = bitCoinData?.values
        if (values != null) {
            for (value in values) {
                xVals.add(getDateFromTimeStamp(value.timeStamp))
            }
        }
        return xVals
    }

    private fun getChartsDataFromArguments(): BitcoinApiResponseModel? =
        arguments?.getParcelable(PARCEL_KEY)


    private fun getDateFromTimeStamp(timeStamp: Long?): String {
        return "$timeStamp"
    }

    private fun getYAxisValues(): List<Entry> {
        val yVals = ArrayList<Entry>()

        val bitCoinData = getChartsDataFromArguments()
        val values = bitCoinData?.values
        var i = 0
        if (values != null) {
            for (value in values) {
                yVals.add(Entry(value.price!!, i++))
            }
        }
        return yVals
    }

}