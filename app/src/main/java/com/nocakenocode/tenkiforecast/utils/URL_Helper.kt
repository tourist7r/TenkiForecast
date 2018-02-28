package com.nocakenocode.tenkiforecast.utils

/**
 * Created by Fahad on 2018-02-28.
 */
public data class URL_Helper(val location_id:String?) {

    public val API_APP_CODE_NO1 = "e9e8cdc09be44201a887b25b5b1fcdcd"
    public val API_APP_CODE_NO2 = ""

    public val BASE_OWM_API_URL = "http://api.openweathermap.org/"
    public val DATA_URL_V25 = "data/2.5/"

    public val METRIC_UNIT = "&units=metric"

    public val LOCATION_NO1_OWM_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=muscat&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"
    public val LOCATION_NO2_OWM_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=london&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"
    public val LOCATION_NO3_OWM_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=tokyo&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"

    public fun getLocation1URL():String{
        return LOCATION_NO1_OWM_API_URL
    }

}