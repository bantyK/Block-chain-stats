package banty.com.repository.utility

/**
 * Created by Banty on 05/01/19.
 *
 * Utility class for a function to check if the device is connected to network or not.
 *
 * This will by default return true because app will always call use the remote server. Local storage is used only for
 * demonstration purpose. See LocalBitcoinRepository.kt for more details
 */
class NetworkUtil {
    fun deviceConnectedToNetwork() : Boolean = true
}