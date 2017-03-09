
package com.example.matt.objecttesting;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Tracking extends AppCompatActivity {
    GpsInfo gps;
    TextView tvPosition;
    int cnt = 0;
    boolean gpsService = true;
    ArrayList<Station> stations;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        Intent intent = getIntent();
        stations = (ArrayList<Station>) intent.getSerializableExtra("stations");

        final Handler mHandler = new Handler();
        gps = GpsInfo.getGpsInfoInstance(this);

        tvPosition = (TextView) findViewById(R.id.tvPosition);

        Thread timer = new Thread("Timer Thread") {
            public void run() {
                while (cnt <= 300) {       // Test for 5Min
                    try {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (gps.isGetLocation()) {
                                    if (!gpsService) {
                                        gpsService = true;
                                        gps.initGPSService();
                                    }
                                    tvPosition.setText("Current Posistion [Latitude]:" + gps.getLatitude() +
                                            ", [Longitude]: " + gps.getLongitude() + "(" + cnt++ + ":" + gps.testCnt + ")");
                                } else {
                                    if (gpsService) {
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void findNearStation(View v) {
        TextView tvNearStation = (TextView)findViewById(R.id.tvNearStations);
        Location currentLocation = gps.getCurrentLocation();
        Location newLocation = new Location("");

        Station stationDistance[] = new Station[stations.size()];

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

        StringBuilder strBuild = new StringBuilder("");
        for(int i=0; i< 5 ; i++){
            strBuild.append(stationDistance[i].getFullName() + ": " + stationDistance[i].getDistance() +"m \n");
        }

        tvNearStation.setText(strBuild);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Tracking Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
