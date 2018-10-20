package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(@NonNull Context context, @NonNull ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake earthquake = getItem(position);

        String originalLocation = earthquake.getLocation();

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String magnitudeFormatted = decimalFormat.format(earthquake.getMagnitude());

        magnitude.setText(magnitudeFormatted);

//        The location and how close to the location it is
        String primaryLocation, locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);

            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.nearLocation);
        locationOffsetView.setText(locationOffset);

        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.Location);
        primaryLocationView.setText(primaryLocation);

        Date dateObject = new Date(earthquake.getTimeInMilliseconds());

        String dateToDisplay = formatDate(dateObject);

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(dateToDisplay);

        TextView time = (TextView) listItemView.findViewById(R.id.time);
        String timeToDisplay = formatTime(dateObject);

        time.setText(timeToDisplay);

        return listItemView;
    }

    private String getNearLocation(String str) {
        int indexSplit = str.indexOf(" of ");

        if (indexSplit == -1) {
            return getContext().getString(R.string.near_the);
        }

        return str.substring(0, indexSplit + 4);
    }

    private String getLocation(String str) {
        int index = str.indexOf(" of ");

        if (index == -1) {
            return str;
        }
        return str.substring(index + 4);
    }

    //    Format the date in milliseconds to Month Day, Year
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(dateObject);
    }

    //    Format the date in milliseconds to Hour:Minute AM/PM
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        return timeFormat.format(dateObject);
    }
}
