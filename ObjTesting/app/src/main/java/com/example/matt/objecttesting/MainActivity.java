package com.example.matt.objecttesting;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.prepare();
    }

    //this function should prep any data structures we'll need
    public void prepare()
    {
        masterList = new ArrayList<Station>();

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

    //finds and returns a Station object matching the argument name
    public Station findStation(String stnName)
    {
        Station result = new Station("Error", "ERR", 0);

        for (int i = 0; i < masterList.size(); i++)
        {
            if (stnName.compareTo(masterList.get(i).getFullName()) == 0)
            {
                result = masterList.get(i);
                break;
            }
        }

        return result;
    }

    //data testing, called by btnTest
    public void testData(View v)
    {
        Spinner station = (Spinner)findViewById(R.id.spnStations);
        String selectedName = station.getSelectedItem().toString();

        Station testStn = findStation(selectedName);
        String testName = testStn.getFullName();
        String testCode = testStn.getCode();
        int testZone = testStn.getZone();

        Toast.makeText(this, testName + " [" + testCode + "], in Zone " + testZone, Toast.LENGTH_LONG).show();
    }
}
