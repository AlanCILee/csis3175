package com.example.brandon.transblink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static ArrayList<Station> masterList;
    public int themeSel = R.style.AppTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent =  getIntent();
        themeSel =  intent.getIntExtra("theme", 0 );
        setTheme(themeSel);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initialize();

        //TESTING: UNCOMMENT AT YOUR OWN PERIL
        DataProcessor.testAlgorithm();
    }

    private void initialize()
    {
        initStations();
    }

    // Station initialize with station information string
    private void initStations()
    {
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
                    data[5],                            //Data5 : Station Connection Info
                    Double.parseDouble(data[6]),        //Data6 : Station Latitude
                    Double.parseDouble(data[7]),        //Data7 : Station Longitude
                    data[8] );                          //Data8 : Line information //but why
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
        Intent intent = new Intent(MainActivity.this, TripPlanner.class);
        intent.putExtra("caller","MainActivity");
        intent.putExtra("theme",themeSel);
        intent.putExtra("stations",masterList);

        startActivity(intent);
    }
    public void goToStationMap(View v)
    {
        Intent intent = new Intent(MainActivity.this, StationMap.class);
        intent.putExtra("theme",themeSel);

        startActivity(intent);
    }
    public void goToNearestStation(View v)
    {
        GPS gps = GPS.getGPSInstance(this);
        if(gps.isGetLocation()) {
            Intent intent = new Intent(MainActivity.this, LocationStation.class);
            intent.putExtra("stations", masterList);
            intent.putExtra("theme",themeSel);
            startActivity(intent);
        }else{
            gps.showSettingsAlert();
        }
    }
    public void goToAboutUs(View v)
    {

        Intent intent = new Intent(MainActivity.this, AboutUs.class);
        intent.putExtra("theme",themeSel);

        startActivity(intent);


    }
    public void goToChangeLog(View v)
    {
        Intent intent = new Intent(MainActivity.this, ChangeLog.class);
        intent.putExtra("theme",themeSel);

        startActivity(intent);
    }
    public void goToThemeChange(View v)
    {
        Intent intent = new Intent(MainActivity.this, ThemeChange.class);
        intent.putExtra("theme",themeSel);

        startActivity(intent);
    }
    public void goToStationInfo(View v)
    {
        Intent intent = new Intent(MainActivity.this, StationInformation.class);
        intent.putExtra("caller","MainActivity");
        intent.putExtra("theme",themeSel);
        intent.putExtra("stations",masterList);

        startActivity(intent);
    }
}
