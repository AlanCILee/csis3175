package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
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



        //old code delete after:
        AutoCompleteTextView start = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewStarting);
        AutoCompleteTextView stop = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewEnding);

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

    public void findRoute(View v)
    {
        // You need to call the static masterlist in the MainActivity because of ????
        Station start = DataProcessor.findStation(MainActivity.masterList, "WTF");
        Station end = DataProcessor.findStation(MainActivity.masterList, "BRD");
        ArrayList<Path> paths = DataProcessor.findRoutes(start, end);
        //System.out.println(DataProcessor.findStation(stations, "CMB").getTransferPoint());

        for (int i = 0; i < paths.size(); i++)
        {
            System.out.println("PATH NO." + (i+1) + " --------------------");
            for (int j = 0; j < paths.get(i).pathStops.size(); j++)
            {
                System.out.println(paths.get(i).pathStops.get(j).getFullName());
            }
        }
    }
}
