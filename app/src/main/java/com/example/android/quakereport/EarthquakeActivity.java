/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    //    The URL that holds the JSON data we want
    private static final String JSON_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
//        final ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
        new earthquakeAsyncTask().execute(JSON_URL);

    }

//    A method to update the UI once the asynctask finishes
        private void updateUI(ArrayList<Earthquake> earthquakeArrayList) {

            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakeArrayList);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//                Get the current earthquake view location
                    Earthquake currentEarthquake = adapter.getItem(position);

//                Parse the url string into uri object
                    Uri uri = Uri.parse(currentEarthquake.getURI());

                    Log.d("EAZY", uri.toString());

//                Create a new intent
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    startActivity(intent);
                }
            });
    }


//    The earthquake ASYNCTASK to handle the
    private class earthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected ArrayList<Earthquake> doInBackground(String... strings) {
            ArrayList<Earthquake> earthquakeList;

            earthquakeList = QueryUtils.fetchEarthquakeData(strings[0]);

            return earthquakeList;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakeList) {
            updateUI(earthquakeList);
        }
    }
}
