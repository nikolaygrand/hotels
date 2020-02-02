package ru.antonov.hotels.screens.hotel

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.data.HotelDetailsModel
import ru.antonov.hotels.repository.HotelsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HotelPresenter : MvpPresenter<HotelView> {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var repository: HotelsRepository

    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        HotelApplication.appComponent.inject(this)
    }

    override fun destroy() {
        disposable.dispose()
    }

    override fun attachView(view: HotelView) {
        disposable += view.getHotelInfo()
            .subscribeOn(Schedulers.io())
            .flatMap { id ->
                repository.getHotelsDetail(id)
                    .subscribeOn(Schedulers.io())
                    .toObservable() }
            .doOnError { view.error(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ hotel: HotelDetailsModel ->
                view.loadHotel(hotel)
            }, {})

        disposable += view.back()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.exit()
            }, { view.error(it)})
    }

    override fun detachView(retainInstance: Boolean) {
        disposable.clear()
    }

    override fun detachView() {
       disposable.clear()
    }
}