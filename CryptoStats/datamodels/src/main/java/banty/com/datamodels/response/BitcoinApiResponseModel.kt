package banty.com.datamodels.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Banty on 05/01/19.
 *
 * Model class for the Bitcoin api response.
 * Example URLs :
 * https://api.blockchain.info/charts/market-price?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/avg-block-size?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/n-transactions?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/mempool-size?timespan=30days&rollingAverage=8hours&format=json
 *
 * Class is made Parcelable for allowing the response to pass to fragments as bundle.
 * Parcelable implementation is generated by Android Studio tool
 */
data class BitcoinApiResponseModel(
    @SerializedName("status") val status: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("unit") val unit: String?,
    @SerializedName("period") val period: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("values") val values: List<Values>?
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Values)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeString(name)
        parcel.writeString(unit)
        parcel.writeString(period)
        parcel.writeString(description)
        parcel.writeTypedList(values)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BitcoinApiResponseModel> {
        override fun createFromParcel(parcel: Parcel): BitcoinApiResponseModel {
            return BitcoinApiResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<BitcoinApiResponseModel?> {
            return arrayOfNulls(size)
        }
    }

}

/**
 * This is the data model of the chart values:
 * x is the timestamp in epoch format
 * y is the value, which could be one of the following 4:
 * Market price
 * Average block size
 * Number of transactions
 * Memory pool size
 * */
data class Values(
    @SerializedName("x") val timeStamp: Long?,
    @SerializedName("y") val price: Float?
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Double::class.java.classLoader) as? Float
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(timeStamp)
        parcel.writeValue(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Values> {
        override fun createFromParcel(parcel: Parcel): Values {
            return Values(parcel)
        }

        override fun newArray(size: Int): Array<Values?> {
            return arrayOfNulls(size)
        }
    }

}
