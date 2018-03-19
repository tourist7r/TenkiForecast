package com.nocakenocode.tenkiforecast.features.location

/*
 * This class is responsible for managing and manipulating all the elements included
 * in google map view, for instance you have autocomplete functionality which will allow you to
 * search for cities by inputting their name in the search box, in addition you can tap the map to pinpoint
 * your desired location.
 *
 * Once marked, you can click on the fab to save the location,
 *
 * This service uses Google Maps API v2 and Google Places API, current API key isn't restricted, please change it and restrict it for production environment.
 *
 * @Author -> Fahad (NoCakeNoCode)
 * Created on 5th of March 2018
 */
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.nocakenocode.tenkiforecast.R
import com.nocakenocode.tenkiforecast.data.model.CurrentWeather
import com.nocakenocode.tenkiforecast.utils.URL_Helper
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.uiThread
import java.net.URL


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var placeAutoComplete: PlaceAutocompleteFragment = PlaceAutocompleteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)

        placeAutoComplete = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment

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

        val mk = mMap.addMarker(MarkerOptions().position(locationCoords))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationCoords))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5f))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.setOnMapClickListener {
            mk.position = it
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
        }

        // when user selects a city from the search box, the city will be marked in the map and the camera will move toward it
        placeAutoComplete.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                //Log.d("Maps", "Place selected: " + place.name)
                mk.position = place.latLng
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
            }

            override fun onError(status: Status) {
                //Log.d("Maps", "An error occurred: " + status)
            }
        })

        floatingActionButtonGMap.onClick {
            // fetch data by coords
            val owmURL = URL_Helper.getCurrentWeatherURLByCoords(mk.position.latitude , mk.position.longitude)

            doAsync {

                val result = URL(owmURL).readText()

                // Fetch Data from API
                val gson = Gson()
                val json = gson.fromJson(result, CurrentWeather::class.java)
                val location = arrayOfNulls<CurrentWeather>(3)
                val slot = intent.getIntExtra("slot" , 0) // fix it more later

                location[slot] = json

                uiThread {
                    /* store location id in prefs and dismiss this activity */

                    // shared preferences instance
                    val sharedPref = this@MapActivity.getSharedPreferences("Wasabi",Context.MODE_PRIVATE)

                    with (sharedPref.edit()) {

                        when (slot) {
                            0 -> putString(getString(R.string.location_id_1), location[slot]!!.location_id)
                            1 -> putString(getString(R.string.location_id_2), location[slot]!!.location_id)
                            else -> putString(getString(R.string.location_id_3), location[slot]!!.location_id)
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
