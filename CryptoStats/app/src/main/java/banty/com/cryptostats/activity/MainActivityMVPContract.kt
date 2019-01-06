package banty.com.cryptostats.activity

/**
 * Created by Banty on 05/01/19.
 */
interface MainActivityMVPContract {

    interface Presenter {

        /*
        * View will start the UI flow by calling this method on presenter.
        * Presenter will first check if there is a network connection, if yes than presenter will call a
        * method on view to attach the OptionsFragment otherwise, No Network popup is displayed
        * */
        fun startUI()

    }

    interface View {
        /*
        * Attach the option Fragment
        * */
        fun showOptionsFragment()

        /*
        * Attach the chart Fragment, The type of graph that needs to be displayed is given by @param chartOptions.
        * chartOption can take one of 4 values defined in Constants.kt in datamodel module
        * */
        fun showChartsFragment(chartOption: String)

        /*
        * Method called by presenter to attach the options fragment
        * */
        fun attachFragment()

        /*
        * Show the no network connectivity message to user
        * */
        fun showNoNetworkMessage()
    }
}