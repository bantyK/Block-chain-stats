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
        fun getChartData(bitCoinData: BitcoinApiResponseModel?): LineData

        /**
         * Fetches the data from the repository. Passes in the mandatory parameter @param timespan
         * to customise the volume of data to be fetched.
         * */
        fun getDataFromRepository(chartOption: String, timespan: String)

        /**
         * Helper function to avoid redrawing the same graph in case user clicks on the
         * same button twice. If user clicks on the button to show the graph which is currently
         * displayed in the screen then presenter will not make the same api call gain.
         *
         * Every time new graph is displayed, the currentTimestamp variable in the presenter is updated
         * */
        fun currentTimeSpanIsDifferentThan(timespan: String): Boolean

        /**
         * View delegates the click handling of time stamp button to presenter via this method
         * It uses the currentTimeSpanIsDifferentThan method to find out if a new graph needs to be drawn,
         * and makes the call to repository to fetch the new data
         * */
        fun handleButtonClick(chartOption: String, timespan: String)

        /**
         * View will call this method if bitcoinApiResponseModel is not present, to fetch new data
         * passing the @param timestamp. Presenter will then make a call to repository and fetches the
         * data to display chart
         * */
        fun setChart(chartOption: String, timespan: String)

    }

    // Interface that will define the View of ChartsFragment
    interface View {
        /**
         * Only method which is exposed to the presenter for displaying the chart
         * After calculating the properties of the graph, presenter will call this method of view passing the @param LineData object
         * which contains config to render the graph in the fragment UI.
         * */
        fun showChart(bitCoinData: BitcoinApiResponseModel?)

        /*
        * Method to hide the progress bar
        * */
        fun hideProgressBar()

        /*
        * Method to show the progress bar
        * */
        fun showProgressBar()

        /*
       * Method for the presenter to call when data fetching is complete and chart is ready to be drawn in the UI
       * passes the @param model which is the response obtained from the repository
       * */
        fun updateUI(model: BitcoinApiResponseModel?)

    }

}