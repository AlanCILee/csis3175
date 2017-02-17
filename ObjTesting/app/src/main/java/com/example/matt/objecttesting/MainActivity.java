package com.example.matt.objecttesting;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
        String[] s = getResources().getStringArray(R.array.stationData);

        for (int i = 0; i < stnData.length; i++)
        {
            String[] data = stnData[i].split(",");
            Station temp = new Station(data[0], data[1], Integer.parseInt(data[2]));
            masterList.add(temp);
        }

        Spinner station = (Spinner)findViewById(R.id.spnStations);
        ArrayAdapter<String> adapter;
        //List<String> s = new ArrayList<String>();

        for (int i = 0; i < masterList.size(); i++)
        {
            s[i] = masterList.get(i).getFullName();
        }

        //String[] testArr = s.toArray(new String[s.size()]);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        station.setAdapter(adapter);
    }

    //THIS IS A TESTING FUNCTION DON'T INCLUDE IT IN ANYTHING RETARD
    public void testData(View v)
    {
        Spinner station = (Spinner)findViewById(R.id.spnStations);
        String selectedName = station.getSelectedItem().toString();

        Station testStn = alGore.findStation(masterList, selectedName);
        String testName = testStn.getFullName();
        String testCode = testStn.getCode();
        int testZone = testStn.getZone();

        Toast.makeText(this, testName + " [" + testCode + "], in Zone " + testZone, Toast.LENGTH_LONG).show();
    }
}
