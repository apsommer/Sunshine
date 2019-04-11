package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Network;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

// TODO (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {

    // TODO (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    public static synchronized void syncWeather(Context context) {

        // TODO (3) Within syncWeather, fetch new weather data
        try {

            // get URL to Udacity network
            URL weatherRequestUrl = NetworkUtils.getUrl(context);

            // get JSON response from URL
            // throws IOException if HTTP request goes bad
            String jsonResponseFromUrl = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            // convert JSON to objects
            // throws JSONObject is error during JSON parsing
            ContentValues[] weatherValues =
                    OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonResponseFromUrl);

            // TODO (4) If we have valid results, delete the old data and insert the new
            if (weatherValues != null && weatherValues.length > 0) {

                // get reference to system ContentResolver
                ContentResolver resolver = context.getContentResolver();

                // delete from database via resolver > provider > db
                resolver.delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);

                // update with new data
                resolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, weatherValues);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

