package com.cathy.weatherapp.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseData {

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("cod")
    @Expose
    var cod: String? = null

    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("list")
    @Expose
    var list: List<City>? = null
}