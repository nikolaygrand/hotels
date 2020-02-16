package ru.antonov.hotels.data

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

@Entity

data class HotelModel(
    @Id
    var uid: Long = 0,
    @SerializedName("id")
    var hotelId            : Long = 0,
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
) : Serializable {
    fun parseSuitesAvailability(): String {
        return suitesAvailability!!.replace(Regex(":"), ",\u00A0")
    }

    fun countFreeRooms(): Int {
        return suitesAvailability!!.split(Regex(":")).size
    }
}