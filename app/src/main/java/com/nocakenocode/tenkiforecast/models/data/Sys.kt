package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
class Sys (
        @SerializedName("type")
        val sys_type: Int,
        @SerializedName("id")
        val sys_id : String,
        @SerializedName("message")
        val sys_message: String,
        @SerializedName("country")
        val sys_country: String,
        @SerializedName("sunrise")
        val sys_sunrise: String,
        @SerializedName("sunset")
        val sys_sunset: String
)