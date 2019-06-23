package com.cathy.weatherapp.model.search

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city")
class City {

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    @Expose
    var id: Long? = null

    @SerializedName("name")
    @Expose
    var name: String = ""

    @Embedded
    @SerializedName("coord")
    @Expose
    var coord: Coord? = null

    @Embedded
    @SerializedName("main")
    @Expose
    var main: Main? = null

    @SerializedName("dt")
    @Expose
    var dt: Long? = null

    @Embedded
    @SerializedName("wind")
    @Expose
    var wind: Wind? = null

    @Ignore
    @SerializedName("sys")
    @Expose
    var sys: Sys? = null

    @Ignore
    @SerializedName("rain")
    @Expose
    var rain: Rain? = null

    @Ignore
    @SerializedName("snow")
    @Expose
    var snow: Float? = null

    @Ignore
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds? = null

    @Ignore
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    @Ignore
    @SerializedName("base")
    @Expose
    var base: String? = null

    @Ignore
    @SerializedName("visibility")
    @Expose
    var visibility: Long? = null

    @Ignore
    @SerializedName("timezone")
    @Expose
    var timezone: Long? = null

    @Ignore
    @SerializedName("cod")
    @Expose
    var cod: Long? = null

    var address: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is City) return false

        if (name != other.name) return false
        if (coord != other.coord) return false
        if (dt != other.dt) return false
        if (wind != other.wind) return false
        if (main != other.main) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (coord?.hashCode() ?: 0)
        result = 31 * result + (dt?.hashCode() ?: 0)
        result = 31 * result + (wind?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "City(id=$id, name='$name', main=$main)"
    }


}