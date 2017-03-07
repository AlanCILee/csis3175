package com.example.matt.objecttesting;

import android.location.Location;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Matt on 12/02/2017.
 */

public class Station implements Serializable
{
    private String fullName;
    private String code;
    private int zone;
    private boolean isOpen;
    private boolean hasConstruction;
    private boolean isTransferPoint;
    private double latitude;
    private double longitude;
//    private Location location;
    private double distance;

    public ArrayList<String> connectingStations; //should this be public?

    //Constructor
    //note: once all this is initialized, there should be no reason to change the information ie no need for mutator functions
    Station(String n, String c, int z, boolean o, boolean con, boolean t, String connectionString, double latitude, double longitude)
    {
        this.fullName = n;
        this.code = c;
        this.zone = z;
        this.isOpen = o;
        this.hasConstruction = con;
        this.isTransferPoint = t;
        this.latitude = latitude;
        this.longitude = longitude;

        connectingStations = new ArrayList<String>();

        if (connectionString.contains(":"))
        {
            String[] connections = connectionString.split(":");

            for (int i = 0; i < connections.length; i++)
            {
                connectingStations.add(connections[i]);
            }
        }
        else
        {
            connectingStations.add(connectionString);
        }
    }

    Station()
    {
        //kek
    }

    public String getFullName()
    {
        return this.fullName;
    }

    public String getCode()
    {
        return this.code;
    }

    public int getZone()
    {
        return this.zone;
    }

    public boolean getOpen()
    {
        return this.isOpen;
    }

    public boolean getConstruction()
    {
        return this.hasConstruction;
    }

    public boolean getTransferPoint()
    {
        return this.isTransferPoint;
    }

    public double getDistance() { return this.distance; }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    public double getLatitude() { return this.latitude; }
    public double getLongitude() { return this.longitude; }


}
