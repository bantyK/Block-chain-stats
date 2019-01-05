package banty.com.cryptostats.charts

import android.graphics.Color
import banty.com.cryptostats.charts.utility.convertEpochTimeToDate
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
        return convertEpochTimeToDate(timeStamp)
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

    /*
    * Sets the properties of the chart
    * */
    override fun setChartData(bitCoinData: BitcoinApiResponseModel?) {
        val xAxisValues = getXAxisValues(bitCoinData)
        val yAxisValues = getYAxisValues(bitCoinData)

        val lineDataSet = LineDataSet(yAxisValues, bitCoinData?.name)
        //Fill the color below the line
        lineDataSet.fillAlpha = 0

        //Set the color of line #039BD2
        lineDataSet.color = Color.rgb(3, 155, 210)

        //Set the line width
        lineDataSet.lineWidth = 2f

        //Set radius of the circles present on the chart line
        lineDataSet.circleRadius = 0.0f

        //Set the text size
        lineDataSet.valueTextSize = 9f

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet)

        val data = LineData(xAxisValues, dataSets)
        chartsFragmentView?.showChart(data)
    }

    override fun getDescription(bitCoinData: BitcoinApiResponseModel?): String? =
        bitCoinData?.description

}
