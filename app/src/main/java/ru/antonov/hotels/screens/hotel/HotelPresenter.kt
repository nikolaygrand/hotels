package ru.antonov.hotels.screens.hotel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.data.HotelDetailsModel
import ru.antonov.hotels.interactors.IHotelInteractor
import ru.antonov.hotels.mvp.BasePresenter
import ru.terrakok.cicerone.Router
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HotelPresenter :
    BasePresenter<HotelView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var interactor: IHotelInteractor

    override fun attachView(view: HotelView) {
        HotelApplication.appComponent.inject(this)
        super.attachView(view)

        disposable += view.getHotelInfo()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .flatMap { id ->
                interactor.getHotelsDetail(id)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ hotel: HotelDetailsModel ->
                view.loadHotel(hotel)
            }, { error ->
                view.error(error)
            })

        disposable += view.back()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.exit()
            }, { view.error(it) })
    }

    override fun detachView() {
        super.detachView()
        disposable.clear()
    }

    override fun destroy() {
        disposable.dispose()
        super.destroy()
    }
}