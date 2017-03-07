package com.example.matt.objecttesting;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class GpsInfo implements LocationListener {
    private static GpsInfo instance = null;
    private final Context mContext;
    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false;

    Location location;
    double lat;
    double lon;
    int testCnt = 0;

    // Minimum GPS Information distance 10m
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // Minimum GPS information update period 1s
    private static final long MIN_TIME_BW_UPDATES = 1000;

    //Singleton implementation of getting instance
    public static GpsInfo getGpsInfoInstance(Context context)     {
        if (instance == null) {
            instance = new GpsInfo(context);
            instance.initGPSService();
        }
        return instance;
    }

    // Private constructor
    private GpsInfo(Context context)     {
        Log.d("GPS","GpsInfo create");
        this.mContext = context;
    }


    public void initGPSService() {
        // Check Permission
        Log.d("GPS","initGPSService");

//        if ( ContextCompat.checkSelfPermission( this.mContext, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission( this.mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("GPS", "Not Permitted");
//            return;
//        }
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
//                    Manifest.permission.READ_CONTACTS)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//
//        }

        try {
            if(isGetLocation()){
                // Getting Data From Network
                if (isNetworkEnabled) {
                    Log.d("GPS","isNetworkEnabled");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        updateCoordinates(location);
                    }
                }

                // Getting Data From GPS
                if (isGPSEnabled) {
                    Log.d("GPS","isGPSEnabled");
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            updateCoordinates(location);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GPS Stop
     * */
//    public void stopUsingGPS(){
//        if(locationManager != null){
//            locationManager.removeUpdates(GpsInfo.this);
//        }
//    }


    public double getLatitude(){
//        if(location != null){
//            lat = location.getLatitude();
//        }
        return lat;
    }


    public double getLongitude(){
//        if(location != null){
//            lon = location.getLongitude();
//        }
        return lon;
    }

    public boolean isGetLocation() {
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        // GPS Setting Status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // Network Setting Status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isGPSEnabled || isNetworkEnabled)
            isGetLocation = true;

        return isGetLocation;
    }


    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("Location Setup");
        alertDialog.setMessage("Location Service is not available. Would you setup this option?");

        // OK
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        // Cancle
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    //Save Location
    private void updateCoordinates(Location location){
        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            Log.d("GPS","updateCoordinates :" + lat + ", "+ lon);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        Log.d("GPS","onLocationChanged");
        testCnt++;
        updateCoordinates(location);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("GPS","onStatusChanged");
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

}