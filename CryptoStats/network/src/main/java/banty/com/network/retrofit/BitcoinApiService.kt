package banty.com.network.retrofit

import banty.com.datamodels.response.BitcoinApiResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Banty on 05/01/19.
 */
interface BitcoinApiService {
    /**
     * Api to fetch the market price response over the time span specified by @param timespan
     * timespan : Duration of the chart, default value is set as 1 year. Possible values (30days, 180days, 1year and 2year)
     * rollingAverage : Duration over which the data should be averaged.
     * format : The format in which the response is fetched : Possible values JSON and CSV, defaults to JSON.
     * */
    @GET("/charts/market-price")
    fun getMarketPrice(
        @Query("timespan") timespan: String = "1year",
        @Query("rollingAverage") rollingAverage: String = "8hours",
        @Query("format") format: String = "json"
    ): Observable<BitcoinApiResponseModel>

    /**
     * Api to fetch the average block size over the specified time span
     * Input same as getMarketPrice
     * */
    @GET("/charts/avg-block-size")
    fun getAverageBlockSize(
        @Query("timespan") timespan: String = "1year",
        @Query("rollingAverage") rollingAverage: String = "8hours",
        @Query("format") format: String = "json"
    ): Observable<BitcoinApiResponseModel>

    /**
     * Api to fetch the number of transactions over the specified time span
     * Input same as getMarketPrice
     * */
    @GET("/charts/n-transactions")
    fun getNumberOfTransactions(
        @Query("timespan") timespan: String = "1year",
        @Query("rollingAverage") rollingAverage: String = "8hours",
        @Query("format") format: String = "json"
    ): Observable<BitcoinApiResponseModel>

    /**
     * Api to fetch the Memory pool size over the specified time span
     * Input same as getMarketPrice
     * */
    @GET("/charts/mempool-size")
    fun getMemoryPoolSize(
        @Query("timespan") timespan: String = "1year",
        @Query("rollingAverage") rollingAverage: String = "8hours",
        @Query("format") format: String = "json"
    ): Observable<BitcoinApiResponseModel>
}