package com.example.brandon.transblink;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Station[] stations;
    String caller;
    double currentLatitude;
    double currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        caller = (String)intent.getStringExtra("caller");

        switch (caller){
            case "nearStation":
                currentLatitude = (Double)intent.getDoubleExtra("currentLatitude",49.262623 );
                currentLongitude = (Double)intent.getDoubleExtra("currentLongitude",-123.069258 );
                stations = (Station[])intent.getSerializableExtra("nearStations");
                break;
        }

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        switch(caller){
            case "nearStation":
                LatLng current = new LatLng(currentLatitude, currentLongitude);
                mMap.addMarker(new MarkerOptions().position(current).title("Your Location")).showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 13));
                for(Station station : stations){
                    addMarker(station);
                }
                break;
        }
    }

    private void addMarker(Station station){
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.stationlogo))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(station.getLatitude(), station.getLongitude()))
                .title(station.getFullName())
        );
    }

}
