package ru.antonov.hotels.activity

import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.mvp.BasePresenter
import ru.antonov.hotels.navigator.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter : BasePresenter<MainView>() {
    @Inject
    lateinit var router: Router

    override fun attachView(view: MainView) {
        HotelApplication.appComponent.inject(this)
        super.attachView(view)
        disposable += view.back()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.exit()
            }, {})
    }

    override fun detachView(retainInstance: Boolean) {
        Log.d("dfgdfgfdg", "t")
    }

    override fun detachView() {
        disposable.clear()
    }

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            router.newRootScreen(Screens.AuthScreen())
        }
    }

    override fun destroy() {
        disposable.dispose()
        super.destroy()
    }
}