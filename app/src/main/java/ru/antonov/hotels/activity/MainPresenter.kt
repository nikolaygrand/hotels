package ru.antonov.hotels.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
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

        disposable += view.firebaseAuthResultIntent()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                view.firebaseAuthResult(result)
                if (result.user != null) {
                    router.newRootScreen(Screens.HomeScreen())
                }
            }
    }

    override fun detachView(retainInstance: Boolean) {
        Log.d("dfgdfgfdg", "t")
    }

    override fun detachView() {
        disposable.clear()
    }

    fun onCreate(savedInstanceState: Bundle?, activity: Activity) {
        if (savedInstanceState == null) {
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.AnonymousBuilder().build()
            )

            activity.startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun destroy() {
        disposable.dispose()
        super.destroy()
    }

    companion object {
        const val RC_SIGN_IN = 1001
    }
}