package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class DailyWeatherList(
        @SerializedName("dt")
        val dt: String? = null,

        @SerializedName("temp")
        val temp: Temp? = null,

        @SerializedName("pressure")
        val pressure: Double? = null,

        @SerializedName("humidity")
        val humidity: Double? = null,

        @SerializedName("weather")
        val weather: List<Weather?>? = null,

        @SerializedName("speed")
        val speed: Double? = null,

        @SerializedName("deg")
        val deg: Double? = null,

        @SerializedName("clouds")
        val clouds: Double? = null,

        @SerializedName("rain")
        val rain: Double? = null

)
