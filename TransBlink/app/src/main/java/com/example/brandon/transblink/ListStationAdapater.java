package com.example.brandon.transblink;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by alee on 3/12/2017.
 */

public class ListStationAdapater extends ArrayAdapter{
    private Activity context;
    private Station[] stations;

    public ListStationAdapater(Activity context, Station[] stations) {
        super(context, R.layout.list_station, stations);
        this.stations = stations;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_station, parent, false);
        }

        TextView txtTitle = (TextView)convertView.findViewById(R.id.tvStationTitle);
        TextView txtDesc = (TextView)convertView.findViewById(R.id.tvStationDesc);

        Button[] lineColors = new Button[2];
        lineColors[0] = (Button)convertView.findViewById(R.id.line1);
        lineColors[0].setBackgroundColor(0);
        lineColors[1] = (Button)convertView.findViewById(R.id.line2);
        lineColors[1].setBackgroundColor(0);

        txtTitle.setText(stations[position].getFullName());
        txtDesc.setText(String.valueOf((int)(stations[position].getDistance()) + "m"));

        Station.Lines[] lines = stations[position].getLines();
        for(int i=0; i<lines.length; i++){
            int color = 0;
            switch (lines[i]){
                case Expo:
                    color = Color.parseColor("#458fef");
                    break;

                case Millennium:
                    color = Color.parseColor("#f4f442");
                    break;

                case Canada:
                    color = Color.parseColor("#41e2f4");
                    break;
            }
           lineColors[i].setBackgroundColor(color);
        }
        return convertView;
    }
}
