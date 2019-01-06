package banty.com.cryptostats.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import banty.com.cryptostats.R
import banty.com.cryptostats.fragments.charts.ChartsFragment
import banty.com.cryptostats.fragments.options.OptionsFragment
import banty.com.cryptostats.utility.NetworkConnectivityUtil

/*
* Launcher Activity
* */
class MainActivity : AppCompatActivity(), MainActivityMVPContract.View, OptionsChangeListener {

    private val logTag = "OptionsActivity"

    private var presenter: MainActivityMVPContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            presenter = MainActivityPresenter(this, NetworkConnectivityUtil(this))
            presenter?.startUI()
        }
    }

    override fun attachFragment() {
        showOptionsFragment()
    }

    /*
    * Add OptionsFragment in the UI
    * */
    override fun showOptionsFragment() {
        addFragment(OptionsFragment.newInstance(), "options_fragment")
    }

    /*
    * Add ChartsFragment in the UI
    * */
    override fun showChartsFragment(chartOption: String) {
        addFragment(ChartsFragment.newInstance(chartOption), "charts_fragment")
    }

    /*
    * Add ChartsFragment in the UI
    * */
    override fun optionChanged(chartOption: String) {
        showChartsFragment(chartOption)
    }


    /**
     * Launches the fragment passed in @param fragmentToAdd with tag @param fragmentTag
     *
     * addToBackStack() is used for handling navigation between OptionsFragment and ChartsFragment.
     * The previous fragment(OptionsFragment) which is being replaced by the new fragment(ChartsFragment) is added to back
     * stack so that when on back press, the previous fragment is launched.
     * */
    private fun addFragment(fragmentToAdd: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentToAdd, fragmentTag)
            .addToBackStack(fragmentTag)
            .commitAllowingStateLoss()
    }

    /*
    * Shows the no network message and close the app when user click OK
    * */
    override fun showNoNetworkMessage() {
        Log.d(logTag, "Network popup displayed")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.label_no_network))
        builder.setMessage(getString(R.string.no_network_message))

        val positiveText = getString(android.R.string.ok)
        //only one button is shown. No negative button
        builder.setPositiveButton(positiveText) { dialog, _ ->
            dialog.dismiss()
            finish() //close the app
        }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Finish the activity if the displayed fragment is the only fragment in the back stack
     * There are no more fragments are available to go back to.
     * */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}
