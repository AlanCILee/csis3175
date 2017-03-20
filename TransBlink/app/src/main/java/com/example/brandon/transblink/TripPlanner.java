package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TripPlanner extends AppCompatActivity {
    Station startStation;
    Station station;
    List<String> stationDisp;
    ArrayList<Station> stations;
    private int themeSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        ThemeChange themeChg = new ThemeChange();
        themeSel = themeChg.findTheme(this);
        setTheme(themeSel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_planner);

        String caller = (String)intent.getSerializableExtra("caller");
        stations = (ArrayList<Station>) intent.getSerializableExtra("stations");
        stationDisp = new ArrayList<>();

        for(int i = 0; i < stations.size(); i++)
        {
            station = stations.get(i);
            stationDisp.add(station.getFullName());
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stationDisp);


        AutoCompleteTextView start = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewStartingStation);
        AutoCompleteTextView stop = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewEndStation);

        start.setAdapter(adapter);
        stop.setAdapter(adapter);




        switch (caller){
            case "LocationStation":
                startStation = (Station)intent.getSerializableExtra("startStation");
                Log.d("startStation",startStation.getFullName());
                start.setText(startStation.getFullName());


                break;

            case "MainActivity":

                break;

        }

        int i =0;

    }
}
