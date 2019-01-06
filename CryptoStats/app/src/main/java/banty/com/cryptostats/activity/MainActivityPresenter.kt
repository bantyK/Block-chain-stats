package banty.com.cryptostats.activity

import banty.com.cryptostats.utility.NetworkConnectivityUtil

/**
 * Created by Banty on 05/01/19.
 *
 * Documentation for the methods are given in Interface. See MainActivityMVPContract.Presenter
 */
class MainActivityPresenter(
    private val view: MainActivityMVPContract.View?,
    private val networkConnectivityUtil: NetworkConnectivityUtil
) :
    MainActivityMVPContract.Presenter {

    override fun startUI() {
        if(networkConnectivityUtil.isNetworkAvailable()) {
            view?.attachFragment()
        } else {
            view?.showNoNetworkMessage()
        }
    }

    private val logTag = "OptionsPresenter"

}