package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class RouteDisplay extends AppCompatActivity {
    public int themeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        ThemeChange themeChg = new ThemeChange();
        themeSel = themeChg.findTheme(this);
        setTheme(themeSel);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_display);

        String caller = (String)intent.getSerializableExtra("caller");
        Path path = (Path)intent.getSerializableExtra("route");
        ArrayList<Station> pathStations = path.pathStops;

        for(int i=0; i< pathStations.size(); i++)
            Log.i("RouteDisplay route :", pathStations.get(i).getFullName());
    }

    public void drawRouteOnMap(){



    }
}
