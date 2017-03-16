package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;


public class StationInformation extends AppCompatActivity {
    ArrayList<Station> stations;
    private int themeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        themeSel =  intent.getIntExtra("theme", 0 );
        setTheme(themeSel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_information);

        stations = (ArrayList<Station>) intent.getSerializableExtra("stations");
    }
}
