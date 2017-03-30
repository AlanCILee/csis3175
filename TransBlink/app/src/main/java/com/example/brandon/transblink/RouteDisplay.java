package com.example.brandon.transblink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RouteDisplay extends AppCompatActivity {
    public int themeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ThemeChange themeChg = new ThemeChange();
        themeSel = themeChg.findTheme(this);
        setTheme(themeSel);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_display);
    }

    public void drawRouteOnMap(){



    }
}
