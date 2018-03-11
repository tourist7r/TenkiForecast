package com.nocakenocode.tenkiforecast.utils

import com.mikepenz.weather_icons_typeface_library.WeatherIcons

/**
 * This helper object was created to help ease the process of fetching correct corresponding weather icons.
 *
 * OpenWeatherMap API provides unix timestamp in UTC +0000, therefore if you want to utilize the full potential of this
 * helper then you will need to uncomment some of the below code and provide a timezone to make the results more accurate.
 *
 * Created by Fahad on 2018-03-11.
 */
object WeatherIconHelper {

    /* fun getWeatherIcon(id:Int,dt: Long): WeatherIcons.Icon{

        // Uncomment and modify when OWM implements TimeZones, otherwise will have to do extra coding work to fetch correct timezone

        val calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Tokyo"))
        calendar.timeInMillis = dt * 1000
        val timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        Log.d("ncnc2" , "Time of Day: " + timeOfDay)
        var night = true

        if (timeOfDay in 6..17) {
            night = false
        }

        return if (!night)
            getDayWeatherIcon(id)
        else getNightWeatherIcon(id)
    }*/

    /**
     * Same as [getWeatherIcon], but only daytime icons

    fun getDayWeatherIcon(id : Int) : WeatherIcons.Icon {
        return WeatherIcons.Icon.valueOf("wic_owm_day_$id")
    }*/

    /**
     * Same as [getWeatherIcon], but only the night icons

    fun getNightWeatherIcon(id : Int) : WeatherIcons.Icon {
        return WeatherIcons.Icon.valueOf("wic_owm_night_$id")
    }*/

    /**
     * Same as [getWeatherIcon], but only neutral icons
     */
    fun getNeutralWeatherIcon(id : Int) : WeatherIcons.Icon {
        return WeatherIcons.Icon.valueOf("wic_owm_$id")
    }

}