package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;


public class StationInformation extends AppCompatActivity {
    ArrayList<Station> stations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_information);

        Intent intent = getIntent();
        stations = (ArrayList<Station>) intent.getSerializableExtra("stations");
    }
}
