package com.cathy.weatherapp.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Wind {

    @SerializedName("speed")
    @Expose
    var speed: Float = 0F

    @SerializedName("deg")
    @Expose
    var deg: Float = 0F

    fun toTextualDescription(): String{
        if (deg > 337.5) return "Северный"
        if (deg > 292.5) return "Северо-западный"
        if (deg > 247.5) return "Западный"
        if (deg > 202.5) return "Юго-западный"
        if (deg > 157.5) return "Южный"
        if (deg > 122.5) return "Юго-восточный"
        if (deg >67.5) return "Восточный"
        if (deg > 22.5)return "Северо-восточный"
        return  ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Wind) return false

        if (speed != other.speed) return false
        if (deg != other.deg) return false

        return true
    }

    override fun hashCode(): Int {
        var result = speed.hashCode()
        result = 31 * result + deg.hashCode()
        return result
    }


}