package com.nocakenocode.tenkiforecast.models.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Fahad on 2018-02-28.
 */
data class City(

        @SerializedName("id")
        val id: String? = null,

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("coord")
        val coordData: Coord? = null,

        @SerializedName("country")
        val country: String? = null,

        @SerializedName("population")
        val population: String? = null
) {

}