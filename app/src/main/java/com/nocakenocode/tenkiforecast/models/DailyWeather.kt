package com.nocakenocode.tenkiforecast.models

import com.google.gson.annotations.SerializedName
import com.nocakenocode.tenkiforecast.models.data.DailyWeatherList


/**
 * Created by Fahad on 2018-02-28.
 */
data class DailyWeather(
        @SerializedName("cod")
        val cod: String,

        @SerializedName("message")
        val message: String,

        @SerializedName("cnt")
        val cnt: String,

        @SerializedName("list")
        val infoDailyWeatherList: List<DailyWeatherList>
)
