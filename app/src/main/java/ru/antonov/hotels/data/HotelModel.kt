package ru.antonov.hotels.data

import com.google.gson.annotations.SerializedName

open class HotelModel(
    @SerializedName("id")
    var id                 : Long = 0,
    @SerializedName("name")
    var name               : String? = null,
    @SerializedName("address")
    var address            : String? = null,
    @SerializedName("stars")
    var stars              : Float? = null,
    @SerializedName("distance")
    var distance           : Float = 0f,
    @SerializedName("suites_availability")
    var suitesAvailability: String? = null
) {
    fun parseSuitesAvailability(): String {
        return suitesAvailability!!.replace(Regex(":"), ",\u00A0")
    }

    fun countFreeRooms(): Int {
        return suitesAvailability!!.split(Regex(":")).size
    }
}