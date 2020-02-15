package ru.antonov.hotels.screens.auth

import com.google.firebase.auth.FirebaseUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.mvp.BasePresenter
import ru.antonov.hotels.navigator.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AuthPresenter : BasePresenter<AuthView>() {

    @Inject
    lateinit var router: Router

    override fun attachView(view: AuthView) {
        HotelApplication.appComponent.inject(this)
        super.attachView(view)

        disposable += view.authSuccess()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { router.newRootScreen(Screens.HomeScreen()) }
    }

    fun authComplete(user: FirebaseUser?) {
        router.newRootScreen(Screens.HomeScreen())
    }
}