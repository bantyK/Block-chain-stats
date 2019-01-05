package banty.com.cryptostats.options

import banty.com.datamodels.response.BitcoinApiResponseModel

/**
 * Created by Banty on 05/01/19.
 */
interface OptionsActivityMVPContract {

    interface Presenter {
        fun getDataFromRepository(timespan: String)
        fun currentTimeSpanIsDifferentThan(timespan: String): Boolean
        fun handleButtonClick(timespan: String)
    }

    interface View {
        fun showChartsFragment(res: BitcoinApiResponseModel)
        fun hideChartContainer()
        fun displayChartContainer()
        fun hideProgressBar()
        fun showProgressBar()
    }
}