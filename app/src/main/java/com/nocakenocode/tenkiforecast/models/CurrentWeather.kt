package com.nocakenocode.tenkiforecast.models

import com.google.gson.annotations.SerializedName
import com.nocakenocode.tenkiforecast.models.data.*

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
        val coordData: Coord,
        @SerializedName("weather")
        val weather: List<Weather?>,

    // internal parameter from OWM server
        @SerializedName("base")
        val base: String,

        @SerializedName("main")
        val main: Main,


        @SerializedName("visibility")
        val visibility: Int,


        @SerializedName("wind")
        val wind: Wind,

        @SerializedName("clouds")
        val clouds: Clouds,

        // Time of data calculation in unix timestamp
        @SerializedName("dt")
        val dt: String,

        @SerializedName("sys")
        val sys: Sys,

        @SerializedName("id")
        val location_id: String,
        @SerializedName("name")
        val location_name: String,

        // internal parameter from OWM server
        @SerializedName("cod")
        val cod: Int


/*
    // For future updates

        @SerializedName("rain")
    val rain: Rain,
        @SerializedName("snow")
    val snow: Snow,
*/

)