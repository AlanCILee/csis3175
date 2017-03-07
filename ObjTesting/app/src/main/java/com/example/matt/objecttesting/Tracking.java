package com.example.matt.objecttesting;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Tracking extends AppCompatActivity {
    GpsInfo gps;
    TextView tvPosition;
    int cnt =0;
    boolean gpsService = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        final Handler mHandler = new Handler();

        gps = GpsInfo.getGpsInfoInstance(this);

        tvPosition = (TextView)findViewById(R.id.tvPosition);

        Thread timer = new Thread("Timer Thread"){
            public void run() {
                while (cnt <= 300 ) {       // Test for 5Min
                    try {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(gps.isGetLocation()){
                                    if(!gpsService){
                                        gpsService = true;
                                        gps.initGPSService();
                                    }
                                    tvPosition.setText("Current Posistion [Latitude]:" + gps.getLatitude() +
                                            ", [Longitude]: " + gps.getLongitude() + "(" + cnt++ + ":"+ gps.testCnt +")");
                                }else{
                                    if(gpsService){
                                        gpsService = false;
                                        gps.showSettingsAlert();
                                        tvPosition.setText("Location Service is off now");
                                    }
                                }

                            }
                        });
                        Thread.sleep(2000);         // Thread is running every 2sec
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();
    }
}
