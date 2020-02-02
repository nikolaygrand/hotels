package ru.antonov.hotels.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ru.antonov.hotels.data.HotelDetailsModel
import ru.antonov.hotels.data.HotelModel

interface IHotelApi {
    @GET("0777.json")
    fun getAllHotels(): Single<ArrayList<HotelModel>>

    @GET("{id}.json")
    fun getHotel(@Path("id") hotelId: Long): Single<HotelDetailsModel>
}