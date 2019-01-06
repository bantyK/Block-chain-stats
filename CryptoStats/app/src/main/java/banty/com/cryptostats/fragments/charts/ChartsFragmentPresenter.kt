package banty.com.cryptostats.fragments.charts

import android.util.Log
import banty.com.cryptostats.fragments.charts.data.BitcoinDataProvider
import banty.com.cryptostats.utility.convertEpochTimeToDate
import banty.com.datamodels.days_30
import banty.com.datamodels.response.BitcoinApiResponseModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import io.reactivex.Scheduler

/**
 * Implementation of ChartsFragmentMVPContract.Presenter.
 *
 * Documentation of all methods is given in the interface. See ChartsFragmentMVPContract
 * */
class ChartsFragmentPresenter(
    private val chartsFragmentView: ChartsFragmentMVPContract.View?,
    private val chartCreator: ChartCreator,
    private val repository: BitcoinDataProvider,
    private val ioScheduler: Scheduler?,
    private val androidScheduler: Scheduler?

) : ChartsFragmentMVPContract.Presenter {

    private val logTag = "ChartsPresenter"

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
    override fun setChart(chartOption: String, timespan: String) {
        chartsFragmentView?.showProgressBar()
        getDataFromRepository(chartOption, timespan)
    }

    private var currentTimeSpan = days_30

    override fun getDataFromRepository(chartOption: String, timespan: String) {
        repository.getBitcoinData(chartOption, timespan)
            .subscribeOn(ioScheduler)
            .observeOn(androidScheduler)
            .subscribe({ bitCoinData ->
                Log.d(logTag, "${bitCoinData.name}")
                chartsFragmentView?.showChart(bitCoinData)
            }, { error ->
                Log.d(logTag, "Error : ${error.message}")
                chartsFragmentView?.showNetworkError()
            })
    }


    override fun getChartData(bitCoinData: BitcoinApiResponseModel?): LineData =
        chartCreator.createChart(getXAxisValues(bitCoinData), getYAxisValues(bitCoinData), bitCoinData?.name)


    override fun currentTimeSpanIsDifferentThan(timespan: String): Boolean {
        return !timespan.contentEquals(currentTimeSpan)
    }

    override fun handleButtonClick(chartOption: String, timespan: String) {
        if (currentTimeSpanIsDifferentThan(timespan)) {
            chartsFragmentView?.showProgressBar()
            getDataFromRepository(chartOption, timespan)
            currentTimeSpan = timespan
        }
    }

}
