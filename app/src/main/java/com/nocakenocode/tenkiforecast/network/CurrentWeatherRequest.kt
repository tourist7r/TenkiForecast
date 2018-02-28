package com.nocakenocode.tenkiforecast.network

import android.os.AsyncTask
import android.util.Log
import java.net.URL

/**
 * This class was defeated by Anko. ggwp :)
 * Created by Fahad on 2018-02-27.
 */
class CurrentWeatherRequest: AsyncTask<String, String, String>()  {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String? {
        return  URL(params[0]).readText()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d("HTTP Result 1" , "$result")
    }
}