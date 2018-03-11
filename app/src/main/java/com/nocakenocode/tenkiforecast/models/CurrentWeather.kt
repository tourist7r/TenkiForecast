package com.nocakenocode.tenkiforecast.models

import com.google.gson.annotations.SerializedName
import com.nocakenocode.tenkiforecast.models.data.*
import java.util.*

/**
 * This class will be used as a structure to hold and represent the current weather data.
 *
 * These POJO data classes are created to represent the data when fetching from API's
 * Serialization helps keeping it intact especially when you're about to
 * obfuscate your source files.
 *
 * Do not modify these unless if you're familiar with OWM API.
 *
 * Created by Fahad on 2018-02-27.
 */
data class CurrentWeather(

        @SerializedName("coord")
        val coordData: Coord? = null,
        @SerializedName("weather")
        val weather: List<Weather?>? = null,

    // internal parameter from OWM server
        @SerializedName("base")
        val base: String? = null,

        @SerializedName("main")
        val main: Main? = null,


        @SerializedName("visibility")
        val visibility: Int? = null,


        @SerializedName("wind")
        val wind: Wind? = null,

        @SerializedName("clouds")
        val clouds: Clouds? = null,

        // Time of data calculation in unix timestamp
        @SerializedName("dt")
        val dt: String? = null,

        @SerializedName("sys")
        val sys: Sys? = null,

        @SerializedName("id")
        val location_id: String? = null,
        @SerializedName("name")
        val location_name: String? = null,

        // internal parameter from OWM server
        @SerializedName("cod")
        val cod: Int? = null


/*
    // For future updates

        @SerializedName("rain")
    val rain: Rain? = null,
        @SerializedName("snow")
    val snow: Snow? = null,
*/

){
    // Converting unix timestamp to valid date on return if available
    var dateTime: Date? = null
        get() {
            if (dt != null) {
                return Date(dt.toLong() * 1000L)
            }
            return null
        }
}