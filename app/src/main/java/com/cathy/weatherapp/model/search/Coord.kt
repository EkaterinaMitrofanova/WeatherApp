package com.cathy.weatherapp.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Coord {

    @SerializedName("lat")
    @Expose
    var lat: Float? = null

    @SerializedName("lon")
    @Expose
    var lon: Float? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Coord) return false

        if (lat != other.lat) return false
        if (lon != other.lon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lat?.hashCode() ?: 0
        result = 31 * result + (lon?.hashCode() ?: 0)
        return result
    }


}