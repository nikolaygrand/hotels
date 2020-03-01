package ru.antonov.hotels.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.yandex.mapkit.MapKitFactory
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.antonov.hotels.BuildConfig
import ru.antonov.hotels.R
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.navigator.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView {

    private val subjectBack = PublishSubject.create<Unit>()
    private val authResultSubject = PublishSubject.create<FirebaseAuthResult>()

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = Navigator(this, R.id.fragment_main_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        HotelApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(BuildConfig.YANDEX_API_KEY)
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)
        presenter.onCreate(savedInstanceState, this)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        subjectBack.onNext(Unit)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            MainPresenter.RC_SIGN_IN -> {
                val response = IdpResponse.fromResultIntent(data)

                if (resultCode == Activity.RESULT_OK) {
                    // Successfully signed in
                    val user = FirebaseAuth.getInstance().currentUser
                    authResultSubject.onNext(FirebaseAuthResult(user = user, authorized = true))
                } else {
                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // response.getError().getErrorCode() and handle the error.

                    authResultSubject.onNext(FirebaseAuthResult(response = response))
                }
            }
        }
    }

    override fun createPresenter() = MainPresenter()
    override fun back() = subjectBack

    override fun chancelAuthManual() {
        val parentLayout: View = findViewById(R.id.fragment_main_layout)
        Snackbar.make(parentLayout, getText(R.string.auth_manual_cancel), LENGTH_LONG).show()
    }

    override fun firebaseAuthResult(auth: FirebaseAuthResult) {
        val parentLayout: View = findViewById(R.id.fragment_main_layout)

        if (auth.authorized) {
            val wellcomeMessage = if (auth.user != null && auth.user!!.displayName != null) {
                getString(R.string.auth_wellcome_with_name, auth.user!!.displayName)
            } else {
                getString(R.string.auth_wellcome_without_name)
            }

            Snackbar.make(
                parentLayout,
                wellcomeMessage,
                LENGTH_LONG
            ).show()
        } else {
            when (auth.response) {
                null -> {
                    Snackbar.make(
                        parentLayout,
                        getText(R.string.auth_manual_cancel),
                        LENGTH_LONG
                    ).show()
                }
                else -> {
                    Snackbar.make(
                        parentLayout,
                        auth.response!!.error?.localizedMessage
                            ?: getText(R.string.unexpected_error),
                        LENGTH_LONG
                    ).show()
                }

            }
        }
    }

    override fun firebaseAuthResultIntent(): Observable<FirebaseAuthResult> = authResultSubject

    companion object {
        const val TAG_TASK_FRAGMENT = "TAG_TASK_FRAGMENT"
    }
}
