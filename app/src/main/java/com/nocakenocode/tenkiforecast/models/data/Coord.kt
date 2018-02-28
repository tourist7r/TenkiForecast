package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-27.
 */
data class Coord (
        @SerializedName("lon")
        val coord_lon: Double? = null,
        @SerializedName("lat")
        val coord_lat: Double? = null
)