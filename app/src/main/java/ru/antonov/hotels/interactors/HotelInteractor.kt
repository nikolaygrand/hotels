package ru.antonov.hotels.interactors

import io.reactivex.Single
import ru.antonov.hotels.cookies.ICookies
import ru.antonov.hotels.data.HotelDetailsModel
import ru.antonov.hotels.repository.IHotelsRepository

class HotelInteractor(val cookies: ICookies, val repository: IHotelsRepository) : IHotelInteractor {
    override fun getHotelsDetail(hotelId: Long): Single<HotelDetailsModel> {
        return repository.getHotelsDetail(hotelId)
    }
}