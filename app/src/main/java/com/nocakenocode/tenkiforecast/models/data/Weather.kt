package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class Weather (
        @SerializedName("id")
        val weather_condition_id: Int? = null,
        @SerializedName("main")
        val weather_main: String? = null,
        @SerializedName("description")
        val weather_description: String? = null,
        @SerializedName("icon")
        val weather_icon: String? = null
)