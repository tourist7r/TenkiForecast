package com.nocakenocode.tenkiforecast.utils

/**
 * This class manages API url queries ,
 * you can provide the following parameters to different functions return a result:
 *
 * Query String -> Location Name
 * Longitude , Latitude -> Gmap Position
 * Location ID -> ID mapped to the location name
 *
 * Created by Fahad on 2018-02-28.
 */
class URL_Helper {

    companion object{
        // Free Account App Code
        val API_APP_CODE_NO1 = "e9e8cdc09be44201a887b25b5b1fcdcd"

        // Premium API provided by another GitHub developer, it may expire at any moment and render the app unusable, set your own app code please.
        val API_APP_CODE_NO2 = "bd5e378503939ddaee76f12ad7a97608"

        // constant URL's
        const val BASE_OWM_API_URL = "http://api.openweathermap.org/"
        const val DATA_URL_V25 = "data/2.5/"
        const val WEATHER_URL = "weather?"
        const val FORECAST_URL = "forecast?"
        const val DAILY_FORECAST_URL = "forecast/daily?"

        // constant parameters
        const val APP_ID_PARAM = "appid" // will be used as the starting parameter (removed & symbol)
        const val METRIC_UNIT_PARAM = "&units=metric"
        const val COUNT_PARAM = "&cnt"
        const val QUERY_PARM = "&q"
        const val LATITUDE_PARAM = "&lat"
        const val LONGITUDE_PARAM = "&lon"
        const val LOCATION_ID_PARAM = "&id"
        const val UNIT_PARAM = "&unit"

        // API URL's for "Current" weather reports - For Debugging Purposes, will be replaced with a URL helper on upcoming updates
        const val SAMPLE_CW_URL1 = "http://api.openweathermap.org/data/2.5/weather?q=muscat&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"
        const val SAMPLE_CW_URL2 = "http://api.openweathermap.org/data/2.5/weather?q=london&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"
        const val SAMPLE_CW_URL3 = "http://api.openweathermap.org/data/2.5/weather?q=tokyo&appid=e9e8cdc09be44201a887b25b5b1fcdcd&units=metric"

        // API URL's for Daily weather reports, the API Key here is borrowed from a different developer and may expire at any moment, this is only for testing purposes.
        const val SAMPLE_DF_URL1 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=muscat&appid=bd5e378503939ddaee76f12ad7a97608&units=metric&cnt=10"
        const val SAMPLE_DF_URL2 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=london&appid=bd5e378503939ddaee76f12ad7a97608&units=metric&cnt=10"
        const val SAMPLE_DF_URL3 = "http://api.openweathermap.org/data/2.5/forecast/daily?q=tokyo&appid=bd5e378503939ddaee76f12ad7a97608&units=metric&cnt=10"

        /*
            Use this function to construct a URL by query to fetch current weather data
            Param -> Query String (Location Name i.e London/California/Tokyo/Muscat...etc)
         */
        fun getCurrentWeatherURLByQuery(query:String):String{
            return "$BASE_OWM_API_URL$DATA_URL_V25$WEATHER_URL" +
                    "$APP_ID_PARAM=$API_APP_CODE_NO1$METRIC_UNIT_PARAM$QUERY_PARM=$query"
        }

        /*
            Use this function to construct a URL by latitude and longitude coordinates to fetch current weather data
            Param 1 -> latitude
            Param 2 -> longitude
         */
        fun getCurrentWeatherURLByCoords(latitude:Double,longitude:Double):String{
            return "$BASE_OWM_API_URL$DATA_URL_V25$WEATHER_URL" +
                    "$APP_ID_PARAM=$API_APP_CODE_NO1$METRIC_UNIT_PARAM$LATITUDE_PARAM=$latitude$LONGITUDE_PARAM=$longitude"
        }

        /*
            Use this function to construct a URL by location ID to fetch current weather data
            Param -> city ID

            For the list of available city ID's have a look at the file provided at http://openweathermap.org/appid
         */
        fun getCurrentWeatherURLByID(locationID:String):String{
            return "$BASE_OWM_API_URL$DATA_URL_V25$WEATHER_URL" +
                    "$APP_ID_PARAM=$API_APP_CODE_NO1$METRIC_UNIT_PARAM$LOCATION_ID_PARAM=$locationID"
        }

        /*---------------------------------------*/

        /*
            Use this function to construct a URL by query to fetch daily forecast data
            Param 1 -> Query String (Location Name i.e London/California/Tokyo/Muscat...etc)
            Param 2 -> Number of days in Integer (Maximum returned by API is 16)
         */
        fun getDailyForecastURLByQuery(query: String, days:Int):String{
            return "$BASE_OWM_API_URL$DATA_URL_V25$DAILY_FORECAST_URL" +
                    "$APP_ID_PARAM=$API_APP_CODE_NO2$METRIC_UNIT_PARAM$COUNT_PARAM=$days$QUERY_PARM=$query"
        }

        /*
            Use this function to construct a URL by coordinates to fetch daily forecast data
            Param 1 -> latitude
            Param 2 -> longitude
            Param 3 -> Number of days in Integer (Maximum returned by API is 16)
         */
        fun getDailyForecastURLByCoords(latitude:Double,longitude:Double, days:Int):String{
            return "$BASE_OWM_API_URL$DATA_URL_V25$DAILY_FORECAST_URL" +
                    "$APP_ID_PARAM=$API_APP_CODE_NO2$METRIC_UNIT_PARAM$COUNT_PARAM=$days$LATITUDE_PARAM=$latitude$LONGITUDE_PARAM=$longitude"
        }

        /*
            Use this function to construct a URL by Location/City ID to fetch daily forecast data
            Param 1 -> City ID
            Param 2 -> Number of days in Integer (Maximum returned by API is 16)

            For the list of available city ID's have a look at the file provided at http://openweathermap.org/appid
         */
        fun getDailyForecastURLByID(locationID:String, days:Int):String{
            return "$BASE_OWM_API_URL$DATA_URL_V25$DAILY_FORECAST_URL" +
                    "$APP_ID_PARAM=$API_APP_CODE_NO2$METRIC_UNIT_PARAM$COUNT_PARAM=$days$LOCATION_ID_PARAM=$locationID"
        }
    }
}