package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //activity teleporters
    public void goToTripPlanner(View v)
    {
        startActivity(new Intent(MainActivity.this,TripPlanner.class));
    }
    public void goToStationMap(View v)
    {
        startActivity(new Intent(MainActivity.this,StationMap.class));
    }
    public void goToNearestStation(View v)
    {
        startActivity(new Intent(MainActivity.this,LocationStation.class));
    }
    public void goToAboutUs(View v)
    {
        startActivity(new Intent(MainActivity.this,AboutUs.class));
    }
    public void goToChangeLog(View v)
    {
        startActivity(new Intent(MainActivity.this,ChangeLog.class));
    }
    public void goToThemeChange(View v)
    {
        startActivity(new Intent(MainActivity.this,ThemeChange.class));
    }
    public void goToStationInfo(View v)
    {
        startActivity(new Intent(MainActivity.this,StationInformation.class));
    }
}
