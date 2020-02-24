package ru.antonov.hotels.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
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

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_SIGN_IN -> {
                val response = IdpResponse.fromResultIntent(data)

                if (resultCode == Activity.RESULT_OK) {
                    // Successfully signed in
                    val user = FirebaseAuth.getInstance().currentUser
                    router.newRootScreen(Screens.HomeScreen())
                } else {
                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // response.getError().getErrorCode() and handle the error.
                }
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 1001
    }
}