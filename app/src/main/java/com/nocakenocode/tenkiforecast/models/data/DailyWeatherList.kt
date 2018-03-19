package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class DailyWeatherList(
        @SerializedName("dt")
        val dt: Long,

        @SerializedName("temp")
        val temp: Temp,

        @SerializedName("pressure")
        val pressure: Double,

        @SerializedName("humidity")
        val humidity: Double,

        @SerializedName("weather")
        val weather: List<Weather>,

        @SerializedName("speed")
        val speed: Double,

        @SerializedName("deg")
        val deg: Double,

        @SerializedName("clouds")
        val clouds: Double,

        @SerializedName("rain")
        val rain: Double

)
