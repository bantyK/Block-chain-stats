package banty.com.cryptostats.fragments.options

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import banty.com.cryptostats.R

/**
 * Created by Banty on 06/01/19.
 */
class OptionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.layout_options_fragment, container, false)
        initViews(v)
        return v
    }

    private fun initViews(v: View?) {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }
}