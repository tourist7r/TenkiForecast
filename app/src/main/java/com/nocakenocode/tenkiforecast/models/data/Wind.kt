package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
class Wind (
        @SerializedName("speed")
        val wind_speed: Double,
        @SerializedName("deg")
        val wind_deg: Double,
        @SerializedName("gust")
        val wind_gust: Double
)
