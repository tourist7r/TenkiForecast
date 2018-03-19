package com.nocakenocode.tenkiforecast.data.model.pojo

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class City(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("coord")
        val coordData: Coord,

        @SerializedName("country")
        val country: String,

        @SerializedName("population")
        val population: String
)