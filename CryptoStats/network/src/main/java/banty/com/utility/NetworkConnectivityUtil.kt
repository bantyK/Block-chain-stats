package banty.com.utility

import android.content.Context
import android.net.ConnectivityManager


/**
 * Created by Banty on 06/01/19.
 *
 * Helper class to find out if device has network connectivity
 */
class NetworkConnectivityUtil(private val context: Context) {

    /*
    * Returns true if device is connected to Network of any type
    * and false if there is no connection
    * */
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}