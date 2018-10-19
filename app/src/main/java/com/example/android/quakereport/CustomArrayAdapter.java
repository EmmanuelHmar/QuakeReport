package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Earthquakes> {

    public CustomArrayAdapter(@NonNull Context context, @NonNull ArrayList<Earthquakes> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView==null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquakes earthquake = getItem(position);

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);

        magnitude.setText(earthquake.getMagnitude());

        TextView city = (TextView) listItemView.findViewById(R.id.city);

        city.setText(earthquake.getCity());

        TextView date = (TextView) listItemView.findViewById(R.id.date);

        date.setText(earthquake.getDate());

        return listItemView;
    }
}
