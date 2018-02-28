package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class Main (
        @SerializedName("temp")
        val temp: Double? = null,
        @SerializedName("pressure")
        val pressure: Double? = null,
        @SerializedName("humidity")
        val humidity: Int? = null,
        @SerializedName("temp_min")
        val temp_min: Double? = null,
        @SerializedName("temp_max")
        val temp_max: Double? = null,
        @SerializedName("sea_level")
        val sea_level: Double? = null,
        @SerializedName("grnd_level")
        val ground_level: Double? = null
)