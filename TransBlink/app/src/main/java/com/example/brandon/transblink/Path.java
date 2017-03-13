package com.example.brandon.transblink;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Matt on 12/02/2017.
 */

public class Path implements Serializable
{
    private Station startStn;
    private Station previousStn;
    private Station endStn;
    private int numStops;
    private int numTransfers;
    private int zoneChanges;
    public ArrayList<Station> pathStops; //should this be public?
    //private ArrayList<Station> master; //I sure hope this works

    Path(Station a, Station b)
    {
        this.startStn = a;
        this.endStn = b;
        this.previousStn = null;
        this.numStops = 0;
        this.zoneChanges = 0;
        this.numTransfers = 0;
        this.pathStops = new ArrayList<Station>();
        this.pathStops.add(a);

        //this.master = MainActivity.masterList;

    }

    public int getNumStops()
    {
        return this.numStops;
    }

    public void setNumStops(int a)
    {
        this.numStops = a;
    }

    public int getNumTransfers()
    {
        return this.numTransfers;
    }

    public void setNumTransfers(int a)
    {
        this.numTransfers = a;
    }

    public int getZoneChanges()
    {
        return this.zoneChanges;
    }

    public void setZoneChanges(int a)
    {
        this . zoneChanges = a;
    }

    public Station getPreviousStn()
    {
        return this.previousStn;
    }

    public void setPreviousStn(Station stn)
    {
        this.previousStn = stn;
    }

    public boolean traverse(Station stn)
    {
        boolean end = false;
        Station current = stn;

        System.out.println("NOW AT " + current.getFullName() + " ON THE WAY TO " + this.endStn.getFullName());

        if (current.equals(this.endStn)) //stop if end
        {
            end = true;
            this.pathStops.add(current); //adds ending station
            System.out.println("DESTINATION FOUND AT " + current.getFullName());
            //return end;
        }
        else
        {
                for (int i = 0; i < current.connectingStations.size(); i++)
                {
                    String code = current.connectingStations.get(i).split("-")[0];

                    if (!end && pathStops.contains((DataProcessor.findStation(MainActivity.masterList, code)))) //if not yet at the destination
                    {
                        this.setPreviousStn(current);
                        System.out.println("TRAVERSE TO " + code);
                        end = traverse(DataProcessor.findStation(MainActivity.masterList, code)); //keep going
                    }

                    if (end)
                    {
                        System.out.println("ADDING " + current.getFullName());
                        this.pathStops.add(current);
                        break; //get out of the loop
                    }
                }
        }

        return end;
    }
}