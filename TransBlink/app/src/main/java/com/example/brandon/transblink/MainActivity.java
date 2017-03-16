package com.example.brandon.transblink;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static ArrayList<Station> masterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initialize();

        //TESTING: UNCOMMENT AT YOUR OWN PERIL
        DataProcessor.testAlgorithm();
    }

    //why is do we need to call this single method that only calls a single method?
    private void initialize()
    {
        try
        {
            initStations();
        }
        catch (IOException ex)
        {
            //some shit goes here
        }
    }

    // Station initialize with station information string
    private void initStations() throws IOException
    {
        masterList = new ArrayList<Station>();

        // FILE READING HERE //////////////////////////////////////////
        AssetManager am = this.getAssets();
        InputStream is = am.open("data.txt");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String[] stnNames = getResources().getStringArray(R.array.stationNames);

        int counter = 0; // there needs to be a better way to do this bit
        String line = "";

        while ((line = br.readLine()) != null)
        {
            //line = br.readLine();
            //System.out.println(line);
            String[] data = line.split(",");
            Station temp = new Station(stnNames[counter],
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

            counter++;
        }

        /*
        String[] stnData = getResources().getStringArray(R.array.stationData);
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
        */
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
        intent.putExtra("stations",masterList);

        startActivity(intent);
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
        Intent intent = new Intent(MainActivity.this, StationInformation.class);
        intent.putExtra("caller","MainActivity");
        intent.putExtra("stations",masterList);

        startActivity(intent);
    }
}
