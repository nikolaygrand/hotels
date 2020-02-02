package ru.antonov.hotels.screens.home

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import ru.antonov.hotels.data.HotelModel

interface HomeView: MvpView {
    fun loadHotels(hotels: ArrayList<HotelModel>)
    fun error(error: Throwable?)

    fun hotelClick(): Observable<HotelModel>

    fun sortHotels(): Observable<SortModel>
}