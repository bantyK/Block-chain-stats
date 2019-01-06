package banty.com.cryptostats.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import banty.com.cryptostats.R
import banty.com.cryptostats.fragments.charts.ChartsFragment


class MainActivity : AppCompatActivity(), MainActivityMVPContract.View {

    private val logTag = "OptionsActivity"

    private lateinit var presenter: MainActivityMVPContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showChartsFragment()
            presenter = MainActivityPresenter(this)
        }
    }

    private fun showChartsFragment() {
        Log.d(logTag, "Show Charts Fragment")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ChartsFragment.newInstance(), "tag")
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("data", 1)
    }

}
