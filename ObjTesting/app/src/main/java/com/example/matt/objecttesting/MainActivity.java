package com.example.matt.objecttesting;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static ArrayList<Station> masterList; //public??
    public static DataProcessor alGore; //AL-GORE-ITHMS

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.prepare();
        alGore = new DataProcessor();
    }

    //this function should prep any data structures we'll need
    public void prepare()
    {
        masterList = new ArrayList<Station>();

        //this shit needs to be in the MainActivity since we're using getResources() to grab the string array
        String[] stnData = getResources().getStringArray(R.array.stationData);
        String[] stnNames = getResources().getStringArray(R.array.stationNames);

        //loop for the masterlist of stuff
        for (int i = 0; i < stnData.length; i++)
        {
            String[] data = stnData[i].split(",");
            Station temp = new Station(stnNames[i], data[0], Integer.parseInt(data[1]), Boolean.parseBoolean(data[2]), Boolean.parseBoolean(data[3]), Boolean.parseBoolean(data[4]), data[5]);
            masterList.add(temp);
        }
    }

    //THIS IS A TESTING FUNCTION DON'T INCLUDE IT IN ANYTHING RETARD
    public void testData(View v)
    {
        Spinner station = (Spinner)findViewById(R.id.spnStations);
        String selectedName = station.getSelectedItem().toString();

        Station testStn = alGore.findStation(masterList, selectedName);
        String testName = testStn.getFullName();
        //String testCode = testStn.getCode();
        int testZone = testStn.getZone();
        boolean testOpen = testStn.getOpen();
        boolean testCon = testStn.getConstruction();
        boolean testTran = testStn.getTransferPoint();

        TextView output = (TextView)findViewById(R.id.txtOutput);
        output.setText(testName + "\n=============================\n");
        output.append("Zone: " + testZone + "\n");
        output.append("Open: " + testOpen + "\n");
        output.append("Construction: " + testCon + "\n");
        output.append("Transfer Point: " + testTran + "\n\n");
        output.append("Connecting Stations \n=============================\n");

        for (int i = 0; i < testStn.connectingStations.size(); i++)
        {
            String[] con = testStn.connectingStations.get(i).split("-");
            String connectorName = alGore.findStation(masterList, con[0]).getFullName();
            String line = alGore.translateLine(con[1]);
            String train = alGore.translateTrain(con[2]);

            output.append(connectorName + " via the " + train + " train on the " + line + "\n");
        }

    }
}
