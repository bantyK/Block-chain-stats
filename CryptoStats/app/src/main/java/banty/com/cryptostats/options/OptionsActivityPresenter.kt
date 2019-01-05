package banty.com.cryptostats.options

import android.util.Log
import banty.com.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Banty on 05/01/19.
 */
class OptionsActivityPresenter(
    private val view: OptionsActivityMVPContract.View?,
    private val repository: Repository
) :
    OptionsActivityMVPContract.Presenter {

    private val logTag = "OptionsPresenter"

    private var currentTimeSpan = days_30

    override fun getDataFromRepository(timespan: String) {
        repository.getMemoryPoolSize(timespan = timespan)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                Log.d(logTag, "${res.values?.size}")
                view?.showChartsFragment(res)
                view?.hideProgressBar()
                view?.displayChartContainer()
            }, { error ->
                Log.d(logTag, "Error : ${error.message}")
            })
    }

    override fun currentTimeSpanIsDifferentThan(timespan: String): Boolean {
        return !timespan.contentEquals(currentTimeSpan)
    }

    override fun handleButtonClick(timespan: String) {
        if (currentTimeSpanIsDifferentThan(timespan)) {
            view?.hideChartContainer()
            view?.showProgressBar()
            getDataFromRepository(timespan)
            currentTimeSpan = timespan
        }
    }
}