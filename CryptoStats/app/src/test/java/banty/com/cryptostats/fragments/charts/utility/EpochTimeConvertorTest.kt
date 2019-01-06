package banty.com.cryptostats.fragments.charts.utility

import banty.com.cryptostats.utility.convertEpochTimeToDate
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Created by Banty on 05/01/19.
 */
class EpochTimeConvertorTest {
    @Test
    fun shouldReturnTimeInDDMMMFormat() {
        assertEquals("07. Dec", convertEpochTimeToDate(1544148720))
    }
}