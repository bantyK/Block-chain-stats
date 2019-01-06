package banty.com.cryptostats.charts

import banty.com.cryptostats.charts.utility.convertEpochTimeToDate
import banty.com.datamodels.response.BitcoinApiResponseModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData

/**
 * Implementation of ChartsFragmentMVPContract.Presenter.
 * Documentation of all methods is given in the interface
 * */
class ChartsFragmentPresenter(private val chartsFragmentView: ChartsFragmentMVPContract.View?, private val chartCreator: ChartCreator) :
    ChartsFragmentMVPContract.Presenter {

    override fun getXAxisValues(bitCoinData: BitcoinApiResponseModel?): ArrayList<String> {
        val xAxisValues = ArrayList<String>()
        val values = bitCoinData?.values
        if (values != null) {
            for (value in values) {
                xAxisValues.add(convertEpochTimeToDate(value.timeStamp))
            }
        }
        return xAxisValues
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
        val data: LineData = chartCreator.createChart(xAxisValues, yAxisValues, bitCoinData?.name)
        chartsFragmentView?.showChart(data)
    }

    override fun getDescription(bitCoinData: BitcoinApiResponseModel?): String? =
        bitCoinData?.description
}
