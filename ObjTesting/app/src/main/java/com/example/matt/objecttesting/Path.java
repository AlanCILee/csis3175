package com.example.matt.objecttesting;

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

    //is this necessary?
    public void addStops(Station station){
        this.pathStops.add(station);
    }

    public boolean traverse(Station stn)
    {
        boolean end = false;
        Station current = stn;
        Station previous = null;

        System.out.println(current.getFullName());
        //System.out.println(current.connectingStations.size());
        //System.out.println(current.connectingStations.get(0));

        if (!current.equals(this.startStn)) //??? I only think I know what I'm doing
        {
            previous = this.getPreviousStn();
            //this.pathStops.add(current);
        }
        else
        {
            this.setPreviousStn(current);
            //this.pathStops.add(current);
        }

        if (current.equals(this.endStn)) //stop if end
        {
            end = true;
            this.pathStops.add(current); //adds ending station
            //return end;
        }
        else
        {
            for (int i = 0; i < current.connectingStations.size(); i++)
            {
                String code = current.connectingStations.get(i).split("-")[0];
                if (!end && valid(previous, MainActivity.alGore.findStation(MainActivity.masterList, code)) && (pathStops.contains(MainActivity.alGore.findStation(MainActivity.masterList, code)) == false)) //holy crap this line
                {
                    this.setPreviousStn(current);
                    end = traverse(MainActivity.alGore.findStation(MainActivity.masterList, code));

                    if (end) //adds the stations "going back up"
                    {
                        this.pathStops.add(current);
                    }

                }
            }
        }

        return end;
    }

    public boolean valid(Station prev, Station going)
    {
        boolean valid = true;

        if (going.equals(prev) || going.equals(null))
            valid = false;

        return valid;
    }


}
