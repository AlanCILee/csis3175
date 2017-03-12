package com.example.brandon.transblink;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class LocationStation extends AppCompatActivity {
    private ArrayList<Station> stations;
    private Station stationDistance[];
    private GPS gps;
    private Location currentLocation = null;
    private double latitude = 0.0;
    private double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_station);

        Intent intent = getIntent();
        stations = (ArrayList<Station>) intent.getSerializableExtra("stations");

        gpsTracking();
        findNearStation();
    }

    private void gpsTracking(){
        final Handler mHandler = new Handler();
        gps = GPS.getGPSInstance(this);
        currentLocation = gps.getCurrentLocation();
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        gps.setOnGPSEventListener(new GPS.GPSEventListener(){
            @Override
            public void onReceivedEvent(){
                Log.d("LocationStation", "onReceivedEvent");
                currentLocation = gps.getCurrentLocation();
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                findNearStation();
            }
        });
    }

    private void findNearStation() {
        Location newLocation = new Location("");
        stationDistance = new Station[stations.size()];

        for (Station station : stations) {
            newLocation.setLatitude(station.getLatitude());
            newLocation.setLongitude(station.getLongitude());
            double distance = currentLocation.distanceTo(newLocation);
            station.setDistance(distance);
            Log.d(station.getFullName(), distance + "m");
        }

        stations.toArray(stationDistance);
        Arrays.sort(stationDistance, new Comparator<Station>() {

            @Override
            public int compare(Station o1, Station o2) {
                return (int)(o1.getDistance() - o2.getDistance());
            }
        });

    }

    public void showMap(View v){
        if( currentLocation != null ){
            Intent intent = new Intent(LocationStation.this, MapsActivity.class);

            intent.putExtra("caller","nearStation");                // set caller information to Map
            intent.putExtra("currentLatitude", latitude);
            intent.putExtra("currentLongitude", longitude);
            intent.putExtra("nearStations", stationDistance);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Please wait receiving location",Toast.LENGTH_SHORT).show();
        }

    }
}
