package banty.com.cryptostats.options

import android.util.Log
import banty.com.repository.Repository
import io.reactivex.Scheduler

/**
 * Created by Banty on 05/01/19.
 */
class OptionsActivityPresenter(
    private val view: OptionsActivityMVPContract.View?,
    private val repository: Repository,
    private val ioScheduler: Scheduler?,
    private val androidScheduler: Scheduler?
) :
    OptionsActivityMVPContract.Presenter {

    private val logTag = "OptionsPresenter"

    private var currentTimeSpan = days_30

    override fun getDataFromRepository(
        timespan: String
    ) {
        repository.getMemoryPoolSize(timespan = timespan)
            .subscribeOn(ioScheduler)
            .observeOn(androidScheduler)
            .subscribe({ res ->
                Log.d(logTag, "${res.values?.size}")
                view?.updateUI(res)
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