package banty.com.cryptostats.fragments.options

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import banty.com.cryptostats.*
import banty.com.cryptostats.activity.MainActivity
import banty.com.cryptostats.activity.OptionsChangeListener
import banty.com.datamodels.CHART_AVERAGE_BLOCK_SIZE
import banty.com.datamodels.CHART_MARKET_PRICE
import banty.com.datamodels.CHART_MEMORY_POOL
import banty.com.datamodels.CHART_NUM_TRANSACTION

/**
 * Created by Banty on 06/01/19.
 *
 * Fragment to give options to user as to what chart among the 4 available chart they want to see.
 * Contains 4 buttons representing 4 available options. The option selected by Fragment will be passed on to MainActivity and
 * from there to ChartsFragment
 */
class OptionsFragment : Fragment(), View.OnClickListener {

    private var optionsChangeListener: OptionsChangeListener? = null

    companion object {
        fun newInstance(): OptionsFragment {
            val args = Bundle()
            val optionsFragment = OptionsFragment()
            optionsFragment.arguments = args
            return optionsFragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.layout_options_fragment, container, false)
        initViews(v)
        return v
    }


    /*
    * Initialise the buttons
    * */
    private fun initViews(v: View?) {
        val marketPriceButton = v?.findViewById<Button>(R.id.button_market_price)
        val averageBlockSizeButton = v?.findViewById<Button>(R.id.button_avg_block_size)
        val numTransactionButton = v?.findViewById<Button>(R.id.button_num_transactions)
        val memoryPoolSizeButton = v?.findViewById<Button>(R.id.button_mem_pool)

        marketPriceButton?.setOnClickListener(this)
        averageBlockSizeButton?.setOnClickListener(this)
        numTransactionButton?.setOnClickListener(this)
        memoryPoolSizeButton?.setOnClickListener(this)
    }

    /**
     * Associates the OptionChangeListener with MainActivity, to send the information back to MainActivity
     * when user selects an option from the buttons in the UI
     * */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity !is MainActivity) {
            throw IllegalStateException("Activity should be an instance of MainActivity")
        }

        optionsChangeListener = context as MainActivity
    }

    /**
     * Send the chart data to MainActivity to showing the graph chosen by User
     * */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_market_price -> optionsChangeListener?.optionChanged(CHART_MARKET_PRICE)
            R.id.button_avg_block_size -> optionsChangeListener?.optionChanged(CHART_AVERAGE_BLOCK_SIZE)
            R.id.button_num_transactions -> optionsChangeListener?.optionChanged(CHART_NUM_TRANSACTION)
            R.id.button_mem_pool -> optionsChangeListener?.optionChanged(CHART_MEMORY_POOL)
        }
    }
}