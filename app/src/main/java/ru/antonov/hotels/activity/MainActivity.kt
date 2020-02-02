package ru.antonov.hotels.activity

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.yandex.mapkit.MapKitFactory
import io.reactivex.subjects.PublishSubject
import ru.antonov.hotels.BuildConfig
import ru.antonov.hotels.R
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.navigator.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainActivity: MvpActivity<MainView, MainPresenter>(), MainView {

    private val subjectBack = PublishSubject.create<Unit>()

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

    override fun createPresenter() = MainPresenter()
    override fun back() = subjectBack

    companion object {
        const val TAG_TASK_FRAGMENT = "TAG_TASK_FRAGMENT"
    }
}
