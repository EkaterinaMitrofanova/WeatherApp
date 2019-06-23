package com.cathy.weatherapp.model.search

import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Main {

    @SerializedName("temp")
    @Expose
    var temp: Float? = null

    @Ignore
    @SerializedName("pressure")
    @Expose
    var pressure: Float? = null

    @Ignore
    @SerializedName("humidity")
    @Expose
    var humidity: Float? = null

    @Ignore
    @SerializedName("temp_min")
    @Expose
    var tempMin: Float? = null

    @Ignore
    @SerializedName("temp_max")
    @Expose
    var tempMax: Float? = null

    @Ignore
    @SerializedName("sea_level")
    @Expose
    var seaLevel: Float? = null

    @Ignore
    @SerializedName("grnd_level")
    @Expose
    var groundLevel: Float? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Main) return false

        if (temp != other.temp) return false

        return true
    }

    override fun hashCode(): Int {
        return temp?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Main(temp=$temp)"
    }


}