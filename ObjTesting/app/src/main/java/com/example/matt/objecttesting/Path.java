package com.example.matt.objecttesting;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Matt on 12/02/2017.
 */

public class Path implements Serializable
{
    private Station startStn;
    private Station endStn;
    private int numStops;
    private int zoneChanges;
    public ArrayList<Station> pathStops; //should this be public?

    Path(Station a, Station b)
    {
        this.startStn = a;
        this.endStn = b;
        this.numStops = 0;
        this.zoneChanges = 0;
        this.pathStops = new ArrayList<Station>();
    }

    public int getNumStops()
    {
        return this.numStops;
    }

    public void setNumStops(int a)
    {
        this.numStops = a;
    }

    public int getZoneChanges()
    {
        return this.zoneChanges;
    }

    public void setZoneChanges(int a)
    {
        this . zoneChanges = a;
    }

    public void addStops(Station station){
        this.pathStops.add(station);
    }

}
