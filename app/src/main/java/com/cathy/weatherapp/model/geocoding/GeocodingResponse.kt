package com.cathy.weatherapp.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GeocodingResponse (

    @SerializedName("plus_code")
    @Expose
    var plusCode: PlusCode? = null,

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null
)