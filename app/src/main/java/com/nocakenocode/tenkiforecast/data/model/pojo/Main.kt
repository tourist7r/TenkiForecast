package com.nocakenocode.tenkiforecast.data.model.pojo

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class Main (
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("pressure")
        val pressure: Double,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("temp_min")
        val temp_min: Double,
        @SerializedName("temp_max")
        val temp_max: Double,
        @SerializedName("sea_level")
        val sea_level: Double,
        @SerializedName("grnd_level")
        val ground_level: Double
)