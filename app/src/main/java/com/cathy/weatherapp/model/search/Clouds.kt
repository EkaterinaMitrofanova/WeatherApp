package com.cathy.weatherapp.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Clouds {

    @SerializedName("all")
    @Expose
    var all: Double? = null

}