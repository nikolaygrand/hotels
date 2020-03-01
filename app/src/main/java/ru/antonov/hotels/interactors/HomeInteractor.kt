package ru.antonov.hotels.interactors

import io.reactivex.Single
import ru.antonov.hotels.cookies.ICookies
import ru.antonov.hotels.data.HotelModel
import ru.antonov.hotels.repository.IHotelsRepository

class HomeInteractor(val cookies: ICookies, val repository: IHotelsRepository) : IHomeInteractor {
    override fun getHotelsList(): Single<ArrayList<HotelModel>> {
        return repository.getHotelsList()
    }
}