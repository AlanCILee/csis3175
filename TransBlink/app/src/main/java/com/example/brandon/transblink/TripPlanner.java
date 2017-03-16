package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class TripPlanner extends AppCompatActivity {
    Station startStation;
    ArrayList<Station> stations;
    private int themeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        themeSel =  intent.getIntExtra("theme", 0 );
        setTheme(themeSel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_planner);

        String caller = (String)intent.getSerializableExtra("caller");

        switch (caller){
            case "LocationStation":
                startStation = (Station)intent.getSerializableExtra("startStation");
                stations = (ArrayList<Station>) intent.getSerializableExtra("stations");
                Log.d("startStation",startStation.getFullName());
                break;

            case "MainActivity":
                stations = (ArrayList<Station>) intent.getSerializableExtra("stations");
                break;

        }

        int i =0;

    }
}
