package ru.antonov.hotels.activity

import android.util.Log
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.navigator.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView> {
    @Inject
    lateinit var router: Router

    val disposable: CompositeDisposable = CompositeDisposable()

    init {
        HotelApplication.appComponent.inject(this)
    }

    override fun destroy() {

    }

    override fun attachView(view: MainView) {
        disposable += view.back()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.exit()
            }, {})

        router.navigateTo(Screens.HomeScreen())
    }

    override fun detachView(retainInstance: Boolean) {
        Log.d("dfgdfgfdg", "t")
    }

    override fun detachView() {

    }
}