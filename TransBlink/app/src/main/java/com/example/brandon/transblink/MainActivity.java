package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Station> masterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initialize();
    }

    private void initialize(){
        initStations();
    }

    // Station initialize with station information string
    private void initStations(){
        masterList = new ArrayList<Station>();

        String[] stnData = getResources().getStringArray(R.array.stationData);
        String[] stnNames = getResources().getStringArray(R.array.stationNames);

        for (int i = 0; i < stnData.length; i++)
        {
            String[] data = stnData[i].split(",");
            Station temp = new Station(stnNames[i],
                    data[0],                            //Data0 : Station Short name
                    Integer.parseInt(data[1]),          //Data1 :
                    Boolean.parseBoolean(data[2]),      //Data2 :
                    Boolean.parseBoolean(data[3]),      //Data3 :
                    Boolean.parseBoolean(data[4]),      //Data4 :
                    data[5],                            //Data0 : Station Connection Info
                    Double.parseDouble(data[6]),        //Data0 : Station Latitude
                    Double.parseDouble(data[7]) );      //Data0 : Station Longitude

            masterList.add(temp);
        }
    }

    // Setter and Getters ---------------------------------------------------
    public ArrayList<Station> getMasterList(){
        return this.masterList;
    }

    //activity teleporters ---------------------------------------------------
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
        GPS gps = GPS.getGPSInstance(this);
        if(gps.isGetLocation()) {
            Intent intent = new Intent(MainActivity.this, LocationStation.class);
            intent.putExtra("stations", masterList);
            startActivity(intent);
        }else{
            gps.showSettingsAlert();
        }
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
