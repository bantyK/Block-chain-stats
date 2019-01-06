package banty.com.cryptostats.utility

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Banty on 05/01/19.
 * Returns the time in the format DD. MMM from epoch time passed as a parameter
 */
fun convertEpochTimeToDate(timestamp: Long?): String {
    if (timestamp != null) {
        Log.d("Timestamp", "Timestamp : $timestamp")

        // Epoch time is in seconds and Date object accepts Long value which is in milliseconds.
        // Hence each epoch value has to be multiplied by 1000 to use it as long value
        val date = Date(timestamp * 1000)

        val format = SimpleDateFormat("dd. MMM", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("Etc/UTC")
        val formatted = format.format(date)
        println(formatted)
        format.timeZone = TimeZone.getDefault()
        return format.format(date)
    }
    return "" // return empty string if timestamp is passed as NULL.
}