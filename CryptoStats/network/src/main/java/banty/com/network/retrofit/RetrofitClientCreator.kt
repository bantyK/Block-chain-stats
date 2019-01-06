package banty.com.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Banty on 05/01/19.
 *
 * Creates and returns an instance of Retrofit for making the http calls.
 * Only a single instance of this will be created which is handled using Dagger's singleton annotation in the module class.
 */
class RetrofitClientCreator {
    companion object {
        //base url for all the bitcoin charts api calls
        private const val BASE_URL = "https://api.blockchain.info/"

        // okhttp client with time out set as 60 seconds
        private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        //create and returns an instance of retrofit builder
        fun createRetrofitClient(): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
        }
    }
}