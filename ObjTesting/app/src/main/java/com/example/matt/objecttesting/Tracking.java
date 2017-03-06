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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        final Handler mHandler = new Handler();
        gps = GpsInfo.getGpsInfoInstance(this);

        tvPosition = (TextView)findViewById(R.id.tvPosition);

        Thread timer = new Thread("Timer Thread"){
            public void run() {
                while (cnt <= 60 ) {

                    try {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvPosition.setText("Current Posistion [Latitude]:" + gps.getLatitude() +
                                        ", [Longitude]: " + gps.getLongitude() + "(" + cnt++ + ":"+ gps.testCnt +")");
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();
    }
}
