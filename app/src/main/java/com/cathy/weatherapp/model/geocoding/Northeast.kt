package com.cathy.weatherapp.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Northeast (

    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
)