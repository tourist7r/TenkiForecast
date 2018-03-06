package com.nocakenocode.tenkiforecast.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.nocakenocode.tenkiforecast.R
import com.nocakenocode.tenkiforecast.models.CurrentWeather
import com.nocakenocode.tenkiforecast.utils.URL_Helper
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.uiThread
import java.net.URL
import android.content.Intent
import android.view.KeyEvent


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // use passed intent data to set marker location

        val locationCoords = LatLng(intent.getDoubleExtra("lat" , 55.2),
                intent.getDoubleExtra("lon" , 2.2))

        var mk = mMap.addMarker(MarkerOptions().position(locationCoords))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationCoords))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5f))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.setOnMapClickListener {
            mk.position = it
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
        }

        floatingActionButtonGMap.onClick {
            // fetch data by coords
            val owmURL = URL_Helper.getCurrentWeatherURLByCoords(mk.position.latitude , mk.position.longitude)

            doAsync {

                val result = URL(owmURL).readText()

                // Fetch Data from API
                val gson = Gson()
                val json = gson.fromJson(result, CurrentWeather::class.java)
                val location = Array(3, { CurrentWeather() })
                val slot = intent.getIntExtra("slot" , 0) // fix it more later

                location[slot] = json

                uiThread {
                    /* store location id in prefs and dismiss this activity */

                    // shared preferences instance
                    val sharedPref = this@MapActivity.getSharedPreferences("Wasabi",Context.MODE_PRIVATE)

                    with (sharedPref.edit()) {

                        when (slot) {
                            0 -> putString(getString(R.string.location_id_1), location[slot].location_id)
                            1 -> putString(getString(R.string.location_id_2), location[slot].location_id)
                            else -> putString(getString(R.string.location_id_3), location[slot].location_id)
                        }

                        commit()
                    }
                    val returnIntent = Intent()
                    returnIntent.putExtra("slot",slot)
                    setResult(Activity.RESULT_OK,returnIntent)
                    finish()
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val data = Intent()
            setResult(Activity.RESULT_CANCELED, data)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val returnIntent = Intent()
                setResult(Activity.RESULT_CANCELED,returnIntent)
                finish()
            } // cancel update
        }
        return true
    }

}
