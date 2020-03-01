package ru.antonov.hotels.interactors

import io.reactivex.Single
import ru.antonov.hotels.data.HotelModel

interface IHomeInteractor {
    fun getHotelsList(): Single<ArrayList<HotelModel>>
}