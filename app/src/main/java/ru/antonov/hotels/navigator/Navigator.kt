package ru.antonov.hotels.navigator

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import io.reactivex.subjects.PublishSubject
import ru.antonov.hotels.application.HotelApplication
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class Navigator (val activity: FragmentActivity, containerId: Int) : SupportAppNavigator(activity, containerId) {

    private val exitClicks = PublishSubject.create<Unit>()
    val exitDispose = exitClicks
        .doOnNext { showSystemMessage("Нажмите ещё раз, чтобы выйти") }
        .buffer(2000, 0, TimeUnit.MILLISECONDS)
        .filter{ it.size > 1}
        .subscribe {
            exitProcess(0)
        }

    init {
        HotelApplication.appComponent.inject(this)
    }

    fun exit() {
        exitClicks.onNext(Unit)
    }

    fun showSystemMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        val TAG = Navigator::class.java.simpleName
    }
}