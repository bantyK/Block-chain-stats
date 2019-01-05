package banty.com.datamodels.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Banty on 05/01/19.
 *
 * Model class for the Bitcoin api response.
 * Example URLs :
 * https://api.blockchain.info/charts/market-price?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/avg-block-size?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/n-transactions?timespan=30days&rollingAverage=8hours&format=json
 * https://api.blockchain.info/charts/mempool-size?timespan=30days&rollingAverage=8hours&format=json
 */
data class BitcoinApiResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("name") val name: String,
    @SerializedName("unit") val unit: String,
    @SerializedName("period") val period: String,
    @SerializedName("description") val description: String,
    @SerializedName("values") val values: List<Values>
)

/**
 * This is the data model of the chart values:
 * @property x is the timestamp in epoch format
 * @property y is the value, which could be one of the following 4:
 * Market price
 * Average block size
 * Number of transactions
 * Memory pool size
 * */
data class Values(
    @SerializedName("x") val timeStamp: Long,
    @SerializedName("y") val price: Double
)
