package ru.antonov.hotels.screens.home

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import ru.antonov.hotels.application.HotelApplication
import ru.antonov.hotels.data.HotelModel
import ru.antonov.hotels.mvp.BasePresenter
import ru.antonov.hotels.navigator.Screens
import ru.antonov.hotels.repository.HotelsRepository
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HomePresenter : BasePresenter<HomeView>() {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var repository: HotelsRepository

    override fun destroy() {
        disposable.dispose()
    }

    override fun attachView(view: HomeView) {
        HotelApplication.appComponent.inject(this)
        super.attachView(view)

        disposable += repository.getHotelsList()
            .doOnSubscribe {
                arrayListOf<HotelModel>()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { hotels: ArrayList<HotelModel> ->
                    view.loadHotels(hotels)
                },
                { error: Throwable? ->
                    view.error(error)
                })

        disposable += view.hotelClick()
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                router.navigateTo(Screens.HotelScreen(it.hotelId))
            }
            .subscribe()

        disposable += view.sortHotels()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { sortModel ->
                view.loadHotels(sortHotels(sortModel))
            }
    }

    override fun detachView(retainInstance: Boolean) {
        disposable.clear()
    }

    override fun detachView() {
        disposable.clear()
    }

    fun sortHotels(model: SortModel): ArrayList<HotelModel> {
        val sortedArray = arrayListOf<HotelModel>()

        when (model.field) {
            SortFieldsEnum.DISTANCE -> {
                sortedArray.addAll(model.data.apply {
                    if (model.direction == SortEnum.ASC) {
                        sortBy { hotelModel -> hotelModel.distance }
                    } else {
                        sortByDescending { hotelModel -> hotelModel.distance }
                    }
                })
            }

            SortFieldsEnum.FREE_ROOMS -> {
                sortedArray.addAll(model.data.apply {
                    if (model.direction == SortEnum.ASC) {
                        sortBy { hotelModel -> hotelModel.countFreeRooms() }
                    } else {
                        sortByDescending { hotelModel -> hotelModel.countFreeRooms() }
                    }
                })
            }

            else -> {
            }
        }

        return sortedArray

    }
}