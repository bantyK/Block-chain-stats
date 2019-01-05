package banty.com.cryptostats.charts

import android.graphics.Color
import banty.com.datamodels.response.BitcoinApiResponseModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

/**
 * Implementation of ChartsFragmentMVPContract.Presenter.
 * Documentation of all methods is given in the interface
 * */
class ChartsFragmentPresenter(private val chartsFragmentView: ChartsFragmentMVPContract.View?) :
    ChartsFragmentMVPContract.Presenter {

    override fun getXAxisValues(bitCoinData: BitcoinApiResponseModel?): ArrayList<String> {
        val xAxisValues = ArrayList<String>()
        val values = bitCoinData?.values
        if (values != null) {
            for (value in values) {
                xAxisValues.add(getDateFromTimeStamp(value.timeStamp))
            }
        }
        return xAxisValues
    }


    private fun getDateFromTimeStamp(timeStamp: Long?): String {
        // Call a utility function for converting the timestamp to date
        return "$timeStamp"
    }

    override fun getYAxisValues(bitCoinData: BitcoinApiResponseModel?): List<Entry> {
        val yAxisValues = ArrayList<Entry>()

        val values = bitCoinData?.values
        var i = 0
        if (values != null) {
            for (value in values) {
                yAxisValues.add(Entry(value.price!!, i++))
            }
        }
        return yAxisValues
    }

    override fun setChartData(bitCoinData: BitcoinApiResponseModel?) {
        val xAxisValues = getXAxisValues(bitCoinData)
        val yAxisValues = getYAxisValues(bitCoinData)

        val set1 = LineDataSet(yAxisValues, bitCoinData?.name)
        set1.fillAlpha = 110
        set1.color = Color.BLACK
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 9f
        set1.setDrawFilled(true)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(xAxisValues, dataSets)
        chartsFragmentView?.showChart(data)
    }


}
