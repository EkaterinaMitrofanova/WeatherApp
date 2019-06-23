package com.cathy.weatherapp.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Rain {

    @SerializedName("1h")
    @Expose
    var lastHour: Float? = null
}