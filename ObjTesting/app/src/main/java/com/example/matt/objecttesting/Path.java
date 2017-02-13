package com.example.matt.objecttesting;

import java.util.ArrayList;

/**
 * Created by Matt on 12/02/2017.
 */

public class Path
{
    private Station startStn;
    private Station endStn;
    private int numStops;
    public ArrayList<Station> pathStops; //should this be public?

    //Constructor should take just the start and end
    Path(Station start, Station end)
    {
        this.startStn = start;
        this.endStn = end;
    }

    public int getNumStops()
    {
        return this.numStops;
    }

    public void setNumStops(int a)
    {
        this.numStops = a;
    }
}
