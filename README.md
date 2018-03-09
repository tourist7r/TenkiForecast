# Tenki Forecast

This is an Android weather forecast application written in Kotlin made for educational purposes, the app has been developed in a compact manner to make it developer and beginner friendly.

App data source is powered by OpenWeatherMap API -> https://openweathermap.org/

Here are a few screenshots of the App in action:

![alt text](https://i.imgur.com/qnJTS1p.jpg)

![alt text](https://i.imgur.com/2jxDhrJ.jpg)

![alt text](https://i.imgur.com/m7P7QtI.jpg)
 
Please be aware that you will need to replace the API keys which can be found in the URL_Helper class inside utils folder as well the Google Map API key.

Currently the first time default starting locations are fixed on Muscat,London,Tokyo for experimentation purposes, whatever location you select next via Google Maps will be saved and reused until changed again.

Current App Features:
- Select cities/location via Google Map API (Change via menu or long hold location button)
- Shows current weather condition.
- Shows daily forecast for the upcoming 10 days.
- Refresh data, press the floating action button at the bottom.
- Offline data - Latest API data fetched will be stored in shared preferences to be used as a backup when there's no network connection.

This project utilizes the following libraries and resources:
- Anko. (SDK, coroutines and commons)
- Iconics-core.
- Weather-Icons.
- Font-Awesome.
- Google gson for easy JSON parsing.
- MPAndroidChart for charts and graphs.
- Google Maps API.
- OpenWeatherMap API.

More features will be added on upcoming updates, you may have a look at the to-do list under projects.

Project is licensed under Apache License 2.0.

Cheers ^-^/
