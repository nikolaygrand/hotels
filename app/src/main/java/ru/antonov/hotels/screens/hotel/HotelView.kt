package ru.antonov.hotels.screens.hotel

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import ru.antonov.hotels.data.HotelDetailsModel

interface HotelView: MvpView {
    fun getHotelInfo(): Observable<Long>
    fun loadHotel(hotel: HotelDetailsModel)
    fun error(error: Throwable?)

    fun back(): Observable<Unit>
}