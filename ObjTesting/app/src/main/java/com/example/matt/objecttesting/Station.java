package com.example.matt.objecttesting;

import java.util.ArrayList;

/**
 * Created by Matt on 12/02/2017.
 */

public class Station
{
    private String fullName;
    private String code;
    private int zone;
    private boolean isOpen = true;
    private boolean hasConstruction = false;
    private boolean isTransferPoint = false;

    public ArrayList<String> connectingStations; //should this be public?

    //Constructor
    //note: once all this is initialized, there should be no reason to change the information ie no need for mutator functions
    Station(String n, String c, int z)
    {
        this.fullName = n;
        this.code = c;
        this.zone = z;
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

}
