package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class Temp(
        @SerializedName("day")
        val day: Double? = null,
        @SerializedName("min")
        val min: Double? = null,
        @SerializedName("max")
        val max: Double? = null,
        @SerializedName("night")
        val night: Double? = null,
        @SerializedName("eve")
        val eve: Double? = null,
        @SerializedName("morn")
        val morn: Double? = null
)