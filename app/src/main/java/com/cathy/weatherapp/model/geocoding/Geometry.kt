package com.cathy.weatherapp.model.geocoding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Geometry (

    @SerializedName("bounds")
    @Expose
    var bounds: Bounds? = null,

    @SerializedName("location")
    @Expose
    var location: Location? = null,

    @SerializedName("location_type")
    @Expose
    private var locationType: String? = null,

    @SerializedName("viewport")
    @Expose
    private var viewport: Viewport? = null
)