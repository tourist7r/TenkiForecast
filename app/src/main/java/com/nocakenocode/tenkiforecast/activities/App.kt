/*

This is a weather forecast application made for educational purposes,
The app is incomplete in some areas however it is functional and can fetch data from the API
and display it on the users screen.

Currently the locations are fixed on Muscat,London,Tokyo for experimentation purposes, this will be further updated later.

Last Updated: 4th March 2018

-Fahad Al Shidhani (NoCakeNoCode)

 */

package com.nocakenocode.tenkiforecast.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.google.gson.Gson
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.context.IconicsContextWrapper
import com.mikepenz.weather_icons_typeface_library.WeatherIcons
import com.nocakenocode.tenkiforecast.R
import com.nocakenocode.tenkiforecast.models.CurrentWeather
import com.nocakenocode.tenkiforecast.models.DailyWeather
import com.nocakenocode.tenkiforecast.renderers.CustomXAxisValueFormatter
import com.nocakenocode.tenkiforecast.utils.URL_Helper
import kotlinx.android.synthetic.main.activity_app.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import java.net.URL
import java.text.SimpleDateFormat
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

    // Store Current Info for Each Location
    private var location = Array(3, { CurrentWeather() })

    // Store Daily Info to be displayed on the graph as shown on OWM website
    private var locationDaily = Array(3, { DailyWeather() })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        // Initialize Current Weather Data
        initCurrentWeather()

        // Daily Forecast data for the Chart
        initDailyForecast()

        // FAB's actions to update all 3 locations
        floatingActionButton4.onClick {
            updateCurrentWeatherInfo(0)
            updateDailyForecast(0)
        }

        floatingActionButton5.onClick {
            updateCurrentWeatherInfo(1)
            updateDailyForecast(1)
        }

        floatingActionButton6.onClick {
            updateCurrentWeatherInfo(2)
            updateDailyForecast(2)
        }



        // change location on long click
        floatingActionButton4.onLongClick {
            val intent = Intent(this@App , MapActivity::class.java)
            intent.putExtra("slot", 0)
            intent.putExtra("lon", location[0].coordData?.coord_lon)
            intent.putExtra("lat", location[0].coordData?.coord_lat)
            startActivityForResult(intent , 1)
        }

        floatingActionButton5.onLongClick {
            val intent = Intent(this@App , MapActivity::class.java)
            intent.putExtra("slot", 1)
            intent.putExtra("lon", location[1].coordData?.coord_lon)
            intent.putExtra("lat", location[1].coordData?.coord_lat)
            startActivityForResult(intent , 1)
        }

        floatingActionButton6.onLongClick {
            val intent = Intent(this@App , MapActivity::class.java)
            intent.putExtra("slot", 2)
            intent.putExtra("lon", location[2].coordData?.coord_lon)
            intent.putExtra("lat", location[2].coordData?.coord_lat)
            startActivityForResult(intent , 1)
        }

        // control the clicking action on the floating action button
        fab.setOnClickListener { view ->

            // Custom animation , Clockwise
            val animCWInf = loadAnimation(applicationContext, R.anim.clockwise_infinite)

            // animate floating action button
            fab.startAnimation(animCWInf)

            // refresh weather and forecast data
            initCurrentWeather()
            initDailyForecast()

            // bar displayed at the bottom of the app
            val snackBar = Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_SHORT)
            snackBar.setAction("Action", null).show()
            snackBar.setAction("Dismiss", {
                fab.clearAnimation()
                snackBar.dismiss()
            })
        }
    }

    // For "Current" Weather reports for each location, will be further optimized in the future.
    private fun initCurrentWeather() {
        populateCurrentWeather(0)
        populateCurrentWeather(1)
        populateCurrentWeather(2)
    }


    // Save data once to be reused for "Current" Weather reports
    private fun populateCurrentWeather(location_position: Int) {

        // Shared preferences instance, used to get previously stored locations
        val sharedPref = this@App?.getSharedPreferences("Wasabi",Context.MODE_PRIVATE) ?: return

        doAsync {

            // Using URL helper class to construct the proper URL by feeding it the location id
            val owmURL = when (location_position) {
                0 -> URL_Helper.getCurrentWeatherURLByID(
                        sharedPref.getString(getString(R.string.location_id_1) , getString(R.string.location_id_1))
                )
                1 -> URL_Helper.getCurrentWeatherURLByID(
                        sharedPref.getString(getString(R.string.location_id_2) , getString(R.string.location_id_2))
                )
                else -> URL_Helper.getCurrentWeatherURLByID(sharedPref.getString(
                        getString(R.string.location_id_3) , getString(R.string.location_id_3))
                )
            }

            val result = URL(owmURL).readText()

            // Fetch Data from API
            val gson = Gson()
            val json = gson.fromJson(result, CurrentWeather::class.java)
            location[location_position] = json

            uiThread {
                updateCurrentWeatherInfo(0)
            }
        }
    }


    /*
        This function will update the UI elements with current weather results fetched earlier and stored in the API

        Argument -> location_position (Integer)

        The argument supplied should be between 0 and 2, the number supplied will denote the location.
     */

    private fun updateCurrentWeatherInfo(location_position: Int) {

        // Make changes in the UI , Always check for null

        location_name.text = if (location[location_position].location_name != null)
            location[location_position].location_name?.toUpperCase() else "N/A"

        // left to right sliding animation
        location_name.animation = loadAnimation(applicationContext, R.anim.left_to_right_slide)

        temperature.text = if (location[location_position].main?.temp != null)
            resources.getString(R.string.temperature_tv, location[location_position].main?.temp) else "N/A"

        // grow fade in animation
        temperature.animation = loadAnimation(applicationContext, R.anim.abc_grow_fade_in_from_bottom)

        if (location[location_position].weather?.get(0)?.weather_description != null) {
            description.text = location[location_position].weather?.get(0)?.weather_description?.toUpperCase()

            weatherIcon.setImageDrawable(IconicsDrawable(applicationContext)
                    .icon(weatherIconHelper("" + description.text))
                    .color(Color.WHITE)
                    .sizeDp(78))

            // right to left slide animation
            weatherIcon.animation = loadAnimation(applicationContext, R.anim.right_to_left_slide)

        } else description.text = "N/A"

        low_high_temp.text = if (location[location_position].main?.temp_min != null && location[location_position].main?.temp_max != null)
            resources.getString(R.string.low_high_temp_tv, location[location_position].main?.temp_min, location[location_position].main?.temp_max) else "N/A"

        humidity2.text = if (location[location_position].main?.humidity != null)
            resources.getString(R.string.humidity_tv, location[location_position].main?.humidity) else "N/A"

        wind_speed2.text = if (location[location_position].wind?.wind_speed != null)
            resources.getString(R.string.wind_speed_tv, location[location_position].wind?.wind_speed) else "N/A"

        if (location[location_position].wind?.wind_deg != null) {
            wind_direction.rotation = location[location_position].wind?.wind_deg!!.toFloat()
            wind_direction2.text = resources.getString(R.string.wind_direction_tv, location[location_position].wind!!.wind_deg!!.toInt())
        } else wind_direction2.text = "N/A"

        pressure2.text = if (location[location_position].main?.pressure != null)
            resources.getString(R.string.pressure_tv, location[location_position].main?.pressure) else "N/A"

        weather_visibility3.text = if (location[location_position].visibility != null)
            resources.getString(R.string.visibility_tv, (location[location_position].visibility!! / 1000)) else "N/A"
    }


    // This helper function is used to determine which icon to use in respect to the weather description
    private fun weatherIconHelper(description: String): WeatherIcons.Icon {

        var result = WeatherIcons.Icon.wic_cloud

        when (description) {
            "CLEAR SKY" -> result = WeatherIcons.Icon.wic_day_sunny
            "FEW CLOUDS" -> result = WeatherIcons.Icon.wic_cloud
            "SCATTERED CLOUDS" -> result = WeatherIcons.Icon.wic_cloudy
            "BROKEN CLOUDS" -> result = WeatherIcons.Icon.wic_cloudy
            "SHOWER RAIN" -> result = WeatherIcons.Icon.wic_showers
            "RAIN" -> result = WeatherIcons.Icon.wic_rain
            "THUNDERSTORM" -> result = WeatherIcons.Icon.wic_thunderstorm
            "SNOW" -> result = WeatherIcons.Icon.wic_snow
            "SHOWER SNOW" -> result = WeatherIcons.Icon.wic_snow
            "MIST" -> result = WeatherIcons.Icon.wic_fog
        }

        return result
    }


    private fun initDailyForecast() {
        populateDailyForecast(0)
        populateDailyForecast(1)
        populateDailyForecast(2)
    }

    // Connect to API using ANKO library and Google GSON to retrieve and store data to be later used for daily forecasts
    private fun populateDailyForecast(location_position: Int) {

        // Shared preferences instance, used to get previously stored locations
        val sharedPref = this@App?.getSharedPreferences("Wasabi",Context.MODE_PRIVATE) ?: return

        doAsyncResult {

            // Using URL helper class to construct the proper URL by feeding it the location id
            val owmURL = when (location_position) {
                0 -> URL_Helper.getDailyForecastURLByID(
                        sharedPref.getString(getString(R.string.location_id_1) , getString(R.string.location_id_1)) , 10
                )
                1 -> URL_Helper.getDailyForecastURLByID(
                        sharedPref.getString(getString(R.string.location_id_2) , getString(R.string.location_id_2)) , 10
                )
                else -> URL_Helper.getDailyForecastURLByID(sharedPref.getString(
                        getString(R.string.location_id_3) , getString(R.string.location_id_3)) , 10
                )
            }

            // Connect to the API and read the entire generated response
            val result = URL(owmURL).readText()
            // Fetch Data from API
            val gson = Gson()
            val json = gson.fromJson(result, DailyWeather::class.java)
            locationDaily[location_position] = json
            // Data manipulation and UI changes
            uiThread {
                updateDailyForecast(0)

                // end fab animation at the end of the last API call by reloading it with a finite animation
                if(location_position == 2) {
                    //fab.clearAnimation()
                    fab.animation = loadAnimation(applicationContext, R.anim.clockwise)
                }
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun updateDailyForecast(location_position: Int) {

        // in this example, a LineChart is initialized from xml
        val chart = findViewById<View>(R.id.chart) as BarChart
        var entries: List<BarEntry> = ArrayList()

        chart.clear()

        val sdf = SimpleDateFormat("D")

        for (i in 0 until 10) {

            val unixSeconds: Long = if(locationDaily[location_position].infoDailyWeatherList?.get(i)?.dt != null)
                locationDaily[location_position].infoDailyWeatherList?.get(i)?.dt!! else -1L
            // convert seconds to milliseconds
            val date = Date(unixSeconds * 1000L)
            val temperature = locationDaily[location_position].infoDailyWeatherList?.get(i)?.temp?.day ?: -1.0

            val dayInYear = sdf.format(date)
            // Add data
            entries += (BarEntry(dayInYear.toFloat(), temperature.toFloat()))

        }

        val dataSet = BarDataSet(entries, "DAILY TEMPERATURE") // add entries to dataset
        dataSet.color = Color.parseColor("#00b1f2")
        dataSet.valueTextColor = Color.WHITE
        chart.legend.textColor = Color.WHITE
        chart.axisLeft.textColor = Color.WHITE
        chart.axisRight.textColor = Color.WHITE
        chart.xAxis.textColor = Color.WHITE

        val xAxisFormatter: IAxisValueFormatter = CustomXAxisValueFormatter()

        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 10
        xAxis.valueFormatter = xAxisFormatter
        xAxis.labelRotationAngle = -45f

        val data = BarData(dataSet)
        data.barWidth = 0.9f // set custom bar width
        chart.data = data
        chart.setFitBars(true) // make the x-axis fit exactly all bars
        chart.description.isEnabled = false
        chart.invalidate() // refresh
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Log.d("rescode","" + resultCode)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data.getIntExtra("slot" , 0)
                //updateCurrentWeatherInfo(result)
                //populateDailyForecast(result)

                //initCurrentWeather()
                //initDailyForecast()

                fab.performClick() // refresh
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                toast("Canceled")
            }
        }
    }//onActivityResult


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
