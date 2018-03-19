package com.nocakenocode.tenkiforecast.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.weather_icons_typeface_library.WeatherIcons
import com.nocakenocode.tenkiforecast.R
import com.nocakenocode.tenkiforecast.models.DailyWeather
import com.nocakenocode.tenkiforecast.utils.WeatherIconHelper
import java.text.SimpleDateFormat
import java.util.*


/**
 * This is an adapter used to fill the view
 * with information related to the weekly forecast.
 *
 * Created by Fahad on 2018-03-10.
 */
class WeeklyForecastAdapter// data is passed into the constructor
(private var context: Context, locationDaily: DailyWeather) : RecyclerView.Adapter<WeeklyForecastAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null
    private var dailyWeather = locationDaily
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("E")

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the view and textview in each row/column
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dailyWeather.infoDailyWeatherList[position]
        holder.bind(
                getDayShortName(data.dt.toString()),
                context.getString(
                        R.string.low_high_temp_tv, data.temp.min,
                        data.temp.max
                ),
                WeatherIconHelper.getNeutralWeatherIcon(data.weather.firstOrNull()?.weather_condition_id!!),
                data.weather.firstOrNull()?.weather_description!!.toUpperCase()
        )
    }

    // total number of rows  <Warning - There used to be a crash due dailyWeather not being initialized somehow,
    // I've placed a condition to fix it, tested over 10 times and its working properly now> I can only hope it won't crash anymore
    override fun getItemCount() =
            dailyWeather.infoDailyWeatherList.size - 3
    // api fetches 10, reduce it to 7

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var day: TextView = itemView.findViewById(R.id.day)
        private var weeklyDayTemperature: TextView = itemView.findViewById(R.id.weekly_day_temperature)
        private var weeklyDayWeatherIcon: ImageView = itemView.findViewById(R.id.weekly_day_weather_icon)
        private var weeklyDayWeatherDescription: TextView = itemView.findViewById(R.id.weekly_day_weather_description)

        // for on click purposes
        /*
        init {
            itemView.setOnClickListener(this)
        }*/

        // bind data method
        fun bind(day: String, weekly_day_temperature: String, weekly_day_weather_icon: WeatherIcons.Icon, weekly_day_weather_description: String) {
            this.day.text = day
            this.weeklyDayTemperature.text = weekly_day_temperature
            this.weeklyDayWeatherIcon.setImageDrawable(
                    IconicsDrawable(context)
                    .icon(weekly_day_weather_icon)
                    .color(Color.WHITE)
                    .sizeDp(42)
            )
            this.weeklyDayWeatherDescription.text = weekly_day_weather_description
        }

        override fun onClick(view: View) {
            //if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }
    }

    private fun getDayShortName(timestamp: String): String {
        val unixSeconds: Long = timestamp.toLong()
        // convert seconds to milliseconds
        val date = Date(unixSeconds * 1000L)
        return sdf.format(date).toUpperCase()
    }

    // convenience method for getting data at click position
    /*
    fun getItem(id: Int): String {
        return dailyWeather.infoDailyWeatherList?.get(id)?.weather?.firstOrNull()?.weather_main!!
    }*/


    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}