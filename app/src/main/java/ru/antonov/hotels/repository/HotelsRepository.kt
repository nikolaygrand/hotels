package ru.antonov.hotels.repository

import io.reactivex.Single
import ru.antonov.hotels.data.HotelModel
import ru.antonov.hotels.network.HotelApi

class HotelsRepository(val api: HotelApi): IHotelsRepository {
    override fun getHotelsList(): Single<ArrayList<HotelModel>> {
        return api.getHotelsApi().getAllHotels()
    }

    override fun getHotelsDetail(hotelId: Long) =
        api.getHotelsApi().getHotel(hotelId)

}