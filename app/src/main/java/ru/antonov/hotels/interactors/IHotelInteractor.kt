package ru.antonov.hotels.interactors

import io.reactivex.Single
import ru.antonov.hotels.data.HotelDetailsModel

interface IHotelInteractor {
    fun getHotelsDetail(hotelId: Long): Single<HotelDetailsModel>
}