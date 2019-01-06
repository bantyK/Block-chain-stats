package banty.com.cryptostats.fragments.charts.utlity

import junit.framework.Assert
import org.junit.Test

/**
 * Created by Banty on 07/01/19.
 */
class EpochTimeConvertorKtTest {
    @Test
    fun shouldReturnTimeInDDMMMFormat() {
        Assert.assertEquals("07. Dec", convertEpochTimeToDate(1544148720))
    }

    @Test
    fun shouldReturnEmptyStringIfTimeStampIsNull() {
        Assert.assertTrue(convertEpochTimeToDate(null).isEmpty())
    }
}