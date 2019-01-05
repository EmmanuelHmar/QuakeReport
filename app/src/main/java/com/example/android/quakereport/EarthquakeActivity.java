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

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    //    The URL that holds the JSON data we want
    private static final String JSON_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private EarthquakeAdapter adapter;
    private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
//        final ArrayList<Earthquake> earthquakes = QueryUtils.extractFeaturesFromJson();
//        new earthquakeAsyncTask().execute(JSON_URL);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        textView = (TextView) findViewById(R.id.textview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

//        Set a text view to view if there is no content to be shown
        earthquakeListView.setEmptyView(textView);
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

//        Check for internet connectivity
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

//        If connected -> fetch data
        if (isConnected) {
            getLoaderManager().initLoader(0, null, this);
        }
//       if not connected to internet then don't load loader and set to no internet
        else {
            progressBar.setVisibility(View.GONE);
            textView.setText("No Internet connection.");
        }

        Log.i(LOG_TAG, "INIT LOADER:" + "After");
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "ONCreateLOADER: " + i);

        return new EarthquakeLoader(EarthquakeActivity.this, JSON_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakeList) {
        Log.i(LOG_TAG, "ONLoadFinished: " + earthquakeList);

//        Set the circular progress bar to gone if loading finished
        progressBar.setVisibility(View.GONE);

//        Set the text for the textview. Does not matter bc won't show if adapter !empty
        textView.setText("There are no earthquakes to be found");

//            Clear the adapter
        adapter.clear();

//            if list has contents then add it to the adapter.
        if (earthquakeList != null && !earthquakeList.isEmpty()) {
            adapter.addAll(earthquakeList);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i("LOADERRESET", "RESET: ");
        adapter.clear();

    }

}
