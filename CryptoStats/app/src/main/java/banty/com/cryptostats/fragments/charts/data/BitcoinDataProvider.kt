package banty.com.cryptostats.fragments.charts.data

import banty.com.datamodels.response.BitcoinApiResponseModel
import io.reactivex.Observable

/**
 * Created by Banty on 06/01/19.
 *
 * Interface which will act as an abstraction for the communication between
 * Fragments and BitCoin repositories.
 *
 * Fragment will access the repository via the implementation of this fragment, this allows
 * complete decoupling between repositories and the UI.
 *
 * Responsible for identifying which api to call for based the the parameters passed.
 */
interface BitcoinDataProvider {
    fun getBitcoinData(option: String, timespan: String): Observable<BitcoinApiResponseModel>
}