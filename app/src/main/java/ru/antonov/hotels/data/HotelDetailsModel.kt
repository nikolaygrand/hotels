package ru.antonov.hotels.data

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class HotelDetailsModel(
    @Id
    var uid: Long = 0,
    @SerializedName("id")
    var hotelId           : Long = 0,
    @SerializedName("name")
    var name               : String? = null,
    @SerializedName("address")
    var address            : String? = null,
    @SerializedName("stars")
    var stars              : Float? = null,
    @SerializedName("distance")
    var distance           : Float = 0f,
    @SerializedName("suites_availability")
    var suitesAvailability: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lon")
    var lon: Double? = null
) {
    fun parseSuitesAvailability(): String {
        return suitesAvailability!!.replace(Regex(":"), ",\u00A0")
    }
}