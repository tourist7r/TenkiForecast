package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class Temp(
        @SerializedName("day")
        val day: Double,
        @SerializedName("min")
        val min: Double,
        @SerializedName("max")
        val max: Double,
        @SerializedName("night")
        val night: Double,
        @SerializedName("eve")
        val eve: Double,
        @SerializedName("morn")
        val morn: Double
)