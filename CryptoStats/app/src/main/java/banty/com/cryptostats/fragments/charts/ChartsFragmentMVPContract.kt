package banty.com.cryptostats.fragments.charts

import banty.com.datamodels.response.BitcoinApiResponseModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData

/**
 * Created by Banty on 05/01/19.
 *
 * MVP Contract for ChartFragment presenter and view
 */
interface ChartsFragmentMVPContract {

    /**
     * Interface that will define the Presenter of ChartsFragment.
     * ChartsFragment View delegates all the processing required for showing the graph to the presenter,
     * which will calculate the graph properties using the BitcoinApiResponseModel passed by View.
     * After calculating all the data points, Presenter will call the only method of view to finally render the graph in the UI
     * */
    interface Presenter {
        /**
         * Returns an Arraylist containing the timestamp information from @param BitcoinApiResponseModel
         * This values in this arraylist are plotted in the X-Axis of the graph
         *
         * */
        fun getXAxisValues(bitCoinData: BitcoinApiResponseModel?): ArrayList<String>

        /**
         * Returns an Arraylist containing one of the following 4 bitcoin related data from @param BitcoinApiResponseModel
         * Market price
         * Average block size
         * Number of transactions
         * Memory pool size
         *
         * This values in this arraylist are plotted in the Y-Axis of the graph
         * */
        fun getYAxisValues(bitCoinData: BitcoinApiResponseModel?): List<Entry>

        /**
         * Customizes the graph to be shown in the fragment.
         * Uses the getXAxisValues and getYAxisValues functions to plot the graph axis
         * Uses the properties of LineDataSet provided by MPAndroidChart to customise the graph
         */


        fun getDataFromRepository(timespan: String)
        fun currentTimeSpanIsDifferentThan(timespan: String): Boolean
        fun handleButtonClick(timespan: String)

        fun setChart(timespan: String)
    }

    // Interface that will define the View of ChartsFragment
    interface View {
        /**
         * Only method which is exposed to the presenter for displaying the chart
         * After calculating the properties of the graph, presenter will call this method of view passing the @param LineData object
         * which contains config to render the graph in the fragment UI.
         * */
        fun showChart(data: LineData, description: String?)

        fun hideProgressBar()
        fun showProgressBar()
        fun updateUI(model: BitcoinApiResponseModel?)

    }

}