package com.cathy.weatherapp.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sys {

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("sunrise")
    @Expose
    var sunrise: Long? = null

    @SerializedName("sunset")
    @Expose
    var sunset: Long? = null

    @SerializedName("type")
    @Expose
    var type: Long? = null

    @SerializedName("id")
    @Expose
    var id: Long = 0

    @SerializedName("message")
    @Expose
    var message: Double? = null
}