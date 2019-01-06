package banty.com.cryptostats.utility

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

/**
 * Created by Banty on 05/01/19.
 */
class EpochTimeConvertorTest {
    @Test
    fun shouldReturnTimeInDDMMMFormat() {
        assertEquals("07. Dec", convertEpochTimeToDate(1544148720))
    }

    @Test
    fun shouldReturnEmptyStringIfTimeStampIsNull() {
        assertTrue(convertEpochTimeToDate(null).isEmpty())
    }
}