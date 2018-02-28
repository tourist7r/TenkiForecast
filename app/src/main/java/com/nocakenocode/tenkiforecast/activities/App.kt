/*

This is a weather forecast application made for educational purposes,
The app is incomplete in some areas however it is functional and can fetch data from the API
and display it on the users screen.

Currently the locations are fixed on Muscat,London,Tokyo for experimentation purposes, this will be further updated later.

Last Updated: 28th Feb 2018

-Fahad Al Shidhani (NoCakeNoCode)

 */

package com.nocakenocode.tenkiforecast.activities

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import com.google.gson.Gson
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.mikepenz.weather_icons_typeface_library.WeatherIcons
import com.nocakenocode.tenkiforecast.R
import com.nocakenocode.tenkiforecast.models.CurrentWeather
import com.nocakenocode.tenkiforecast.models.DailyWeather
import kotlinx.android.synthetic.main.activity_app.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.uiThread
import java.net.URL
import java.util.*

class App : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */

    // API URL's for "Current" weather reports
    val url1 = "http://api.openweathermap.org/data/2.5/weather?q=muscat&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"
    val url2 = "http://api.openweathermap.org/data/2.5/weather?q=london&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"
    val url3 = "http://api.openweathermap.org/data/2.5/weather?q=tokyo&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"

    // API URL's for Daily weather reports, the API Key here is borrowed from a different developer and may expire at any moment, this is only for testing purposes.
    val url4 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=muscat&appid=bd5e378503939ddaee76f12ad7a97608&units=metric&cnt=16"
    val url5 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=london&appid=bd5e378503939ddaee76f12ad7a97608&units=metric&cnt=16"
    val url6 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=tokyo&appid=bd5e378503939ddaee76f12ad7a97608&units=metric&cnt=16"

    // Store Current Info for Each Location
    private var location = Array<CurrentWeather>(3, { CurrentWeather() } )

    // Store Daily Info to be displayed on the graph as shown on OWM website
    private var location_daily = Array<DailyWeather>(3, { DailyWeather() } )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)


        // control the clicking action on the floating action button
        fab.setOnClickListener { view ->

            // Custom animation , Clockwise and Counter-Clockwise
            val anim_cw = AnimationUtils.loadAnimation(applicationContext, R.anim.clockwise)
            val anim_ccw = AnimationUtils.loadAnimation(applicationContext, R.anim.anticlockwise)

            fab.startAnimation(anim_cw)

            // Operation to fetch info from the API to prepare weather info data
            repopulateWeatherInfo()

            // Bar displayed at the bottom of the app
            val snackbar = Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_LONG)
            snackbar.setAction("Action", null).show()
            snackbar.setAction("Dismiss", View.OnClickListener {
                fab.clearAnimation()
                snackbar.dismiss()
            })

        }

        // One time call to fetch weather info
        repopulateWeatherInfo()

        // FAB's actions to update all 3 locations
        floatingActionButton4.onClick {
            updateWeatherInfo(0)
        }

        floatingActionButton5.onClick {
            updateWeatherInfo(1)
        }

        floatingActionButton6.onClick {
            updateWeatherInfo(2)
        }

        // Daily Forecast data for the Graph - Under development
        fetchDailyForecast(0)
        fetchDailyForecast(1)
        fetchDailyForecast(2)
    }

    // Connect to API using ANKO library and Google GSON to retrieve and store data to be later used for daily forecasts
    private fun fetchDailyForecast(location_position:Int):Boolean{
        doAsync {

            // Proper URL selection
            var owm_url = ""
            if(location_position == 0)
                owm_url = url4
            else if(location_position == 1)
                owm_url = url5
            else
                owm_url = url6

            // Connect to the API and read the entire generated response
            val result = URL(owm_url).readText()

            // Data manipulation and UI changes
            uiThread {

                // Fetch Data from API
                val gson = Gson()
                val json = gson.fromJson(result, DailyWeather::class.java)
                location_daily[location_position] = json

                // Graph
                val graph = findViewById<View>(R.id.graph) as GraphView
                val series = LineGraphSeries(arrayOf())

                graph.removeAllSeries()

                var d1:Date
                val calendar = Calendar.getInstance()
                for (i in 0..16 - 1){
                    d1 = calendar.time
                    series.appendData(DataPoint(
                            d1,
                            (""+location_daily[location_position].infoDailyWeatherList?.get(i)?.temp?.day).toDouble()
                    ),true,16)

                    calendar.add(Calendar.DATE, 1)
                }

                graph.addSeries(series)

                // styling series
                series.setTitle("Temperature");
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10f);
                series.setThickness(8);

                fab.clearAnimation()
            }
        }
        return true
    }



    // For "Current" Weather reports for each location, will be further optimized in the future.
    private fun repopulateWeatherInfo():Boolean{
        populateWeatherInfo(0)
        populateWeatherInfo(1)
        populateWeatherInfo(2)

        return true
    }

    // Save data once to be reused for "Current" Weather reports
    private fun populateWeatherInfo(location_position:Int):Boolean{
        doAsync {

            var owm_url = ""
            if(location_position == 0)
                owm_url = url1
            else if(location_position == 1)
                owm_url = url2
            else
                owm_url = url3

            val result = URL(owm_url).readText()
            uiThread {

                // Fetch Data from API
                val gson = Gson()
                val json = gson.fromJson(result, CurrentWeather::class.java)
                location[location_position] = json

                // Make changes in the UI
                location_name.text = json.location_name

                temperature.text = "" + json.main?.temp
                description.text = "" + json.weather?.get(0)?.weather_description?.toUpperCase()


                weatherIcon.setImageDrawable(IconicsDrawable(applicationContext)
                        .icon(weatherIconHelper("" + json.weather?.get(0)?.weather_description?.toUpperCase()))
                        .color(Color.WHITE)
                        .sizeDp(78))

                low_high_temp.text = "" + json.main?.temp_min + "°/" + json.main?.temp_max + "°"
                humidity2.text = "" + json.main?.humidity + "%"

                var temp:Double? = json?.wind?.wind_speed

                wind_speed2.text = "" + temp + " /KM"
                if(wind_direction2 != null)
                wind_direction2.text = "" + json.wind?.wind_deg?.toInt() + "°"

                var rot:Float? = json.wind?.wind_deg?.toFloat()
                if(rot != null)
                    wind_direction.rotation = ("" + rot).toFloat() //nobody can stop me
                pressure2.text = "" + json.main?.pressure + " hPa"
                weather_visibility3.text = "" + if (json.visibility != null) ("" + json.visibility/1000.0 + " KM") else " N/A"

                fab.clearAnimation()
            }
        }

        return true
    }

    private fun updateWeatherInfo(location_position:Int):Boolean{

        location_name.text = location[location_position].location_name

        temperature.text = "" + location[location_position].main?.temp
        description.text = "" + location[location_position].weather?.get(0)?.weather_description?.toUpperCase()

        weatherIcon.setImageDrawable(IconicsDrawable(applicationContext)
            .icon(weatherIconHelper("" + location[location_position].weather?.get(0)?.weather_description?.toUpperCase()))
            .color(Color.WHITE)
            .sizeDp(78))
        low_high_temp.text = "" + location[location_position].main?.temp_min + "°/" + location[location_position].main?.temp_max + "°"
        humidity2.text = "" + location[location_position].main?.humidity + "%"

        var temp:Double? = location[location_position]?.wind?.wind_speed

        wind_speed2.text = "" + temp + " /KM"
        if(wind_direction2 != null)
        wind_direction2.text = "" + location[location_position].wind?.wind_deg?.toInt() + "°"

        var rot:Float? = location[location_position].wind?.wind_deg?.toFloat()

        if(rot != null)
        wind_direction.rotation = ("" + rot).toFloat() //nobody can stop the Ginyu Force!

        if(location[location_position].main?.pressure != null)
        pressure2.text = "" + location[location_position].main?.pressure + " hPa"
        weather_visibility3.text = "" + if (location[location_position].visibility != null) ("" + location[location_position].visibility+ " M") else " N/A"

        return true
    }


    // This helper function is used to determine which icon to use in respect to the weather description
    private fun weatherIconHelper(description: String):WeatherIcons.Icon{

        var result: WeatherIcons.Icon = WeatherIcons.Icon.wic_cloud

        if(description.equals("CLEAR SKY"))
            result = WeatherIcons.Icon.wic_day_sunny
        else if(description.equals("FEW CLOUDS"))
            result = WeatherIcons.Icon.wic_cloud
        else if(description.equals("SCATTERED CLOUDS"))
            result = WeatherIcons.Icon.wic_cloudy
        else if(description.equals("BROKEN CLOUDS"))
            result = WeatherIcons.Icon.wic_cloudy
        else if(description.equals("SHOWER RAIN"))
            result = WeatherIcons.Icon.wic_showers
        else if(description.equals("RAIN"))
            result = WeatherIcons.Icon.wic_rain
        else if(description.equals("THUNDERSTORM"))
            result = WeatherIcons.Icon.wic_thunderstorm
        else if(description.equals("SNOW"))
            result = WeatherIcons.Icon.wic_snow
        else if(description.equals("SHOWER SNOW"))
            result = WeatherIcons.Icon.wic_snow
        else if(description.equals("MIST"))
            result = WeatherIcons.Icon.wic_fog

        return result
    }

    // Inject into context to use Android Icons
    // More from https://github.com/mikepenz/Android-Iconics
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_app, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
