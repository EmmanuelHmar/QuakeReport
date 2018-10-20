package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        GradientDrawable gradientDrawable = (GradientDrawable) magnitudeView.getBackground();

        int bgColor = getMagnitudeColor(earthquake.getMagnitude());

        gradientDrawable.setColor(bgColor);

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String magnitudeFormatted = decimalFormat.format(earthquake.getMagnitude());

        magnitudeView.setText(magnitudeFormatted);

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

    private int getMagnitudeColor(Double magnitude) {
        int color;
        switch ((int) Math.floor(magnitude)) {

            case 0:
            case 1:
                color = R.color.magnitude1;
                break;
            case 2:
                color = R.color.magnitude2;
                break;
            case 3:
                color = R.color.magnitude3;
                break;
            case 4:
                color = R.color.magnitude4;
                break;
            case 5:
                color = R.color.magnitude5;
                break;
            case 6:
                color = R.color.magnitude6;
                break;
            case 7:
                color = R.color.magnitude7;
                break;
            case 8:
                color = R.color.magnitude8;
                break;
            case 9:
                color = R.color.magnitude9;
                break;
            default:
                color = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), color);
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
