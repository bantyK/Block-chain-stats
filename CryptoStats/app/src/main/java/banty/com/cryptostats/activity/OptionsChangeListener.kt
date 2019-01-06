package banty.com.cryptostats.activity

/**
 * Interface that will act as a listener to pass the information of user choice from Options
 * Fragment to MainActivity, which will then pass it to ChartsFragment to display the chart chosen by User
 *
 * Added to avoid the direct coupling between OptionsFragment and MainActivity
 * */
interface OptionsChangeListener {
    fun optionChanged(chartOption: String)
}
