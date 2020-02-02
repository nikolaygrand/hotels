package ru.antonov.hotels.repository

import io.reactivex.Single
import ru.antonov.hotels.data.HotelDetailsModel
import ru.antonov.hotels.data.HotelModel

interface IHotelsRepository {
    fun getHotelsList(): Single<ArrayList<HotelModel>>
    fun getHotelsDetail(hotelId: Long): Single<HotelDetailsModel>
}