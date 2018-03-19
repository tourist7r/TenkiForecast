package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class Weather (
        @SerializedName("id")
        val weather_condition_id: Int,
        @SerializedName("main")
        val weather_main: String,
        @SerializedName("description")
        val weather_description: String,
        @SerializedName("icon")
        val weather_icon: String
)