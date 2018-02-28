package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
class Sys (
        @SerializedName("type")
        val sys_type: Int? = null,
        @SerializedName("id")
        val sys_id : String? = null,
        @SerializedName("message")
        val sys_message: String? = null,
        @SerializedName("country")
        val sys_country: String? = null,
        @SerializedName("sunrise")
        val sys_sunrise: String? = null,
        @SerializedName("sunset")
        val sys_sunset: String? = null
)