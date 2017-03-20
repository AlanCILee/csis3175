package com.example.brandon.transblink;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Matt on 17/02/2017.
 * This class should contain all the data processing functions
 */

public class DataProcessor
{
    //finds and returns a Station object matching the argument name
    public static Station findStation(ArrayList<Station> searchArr,String searchStn)
    {
        Station result = null;

        for (int i = 0; i < searchArr.size(); i++)
        {
            if (searchStn.length() > 3) //allow for full name or code search
            {
                if (searchStn.compareTo(searchArr.get(i).getFullName()) == 0)
                {
                    result = searchArr.get(i);
                    break;
                }
            }
            else
            {
                if (searchStn.compareTo(searchArr.get(i).getCode()) == 0)
                {
                    result = searchArr.get(i);
                    break;
                }
            }
        }

        return result;
    }

    //turns a line code into a not line code
    public static String translateLine(String lineCode)
    {
        if (lineCode.equals("EXPO"))
            return "Expo Line";
        else if (lineCode.equals("MILL"))
            return "Millenium Line";
        else
            return "Canada Line";
    }

    //turns a train code into a not train code
    public static String translateTrain(String trainCode)
    {
        if (trainCode.equals("PWAYU"))
            return "Production Way/University";
        else if (trainCode.equals("KGEORGE"))
            return "King George";
        else if (trainCode.equals("LLDOUG"))
            return "Lafarge Lake-Douglas";
        else if (trainCode.equals("VCCCL"))
            return "VCC-Clark";
        else if (trainCode.equals("RICHBR"))
            return "Richmond-Brighouse";
        else if (trainCode.equals("YVRA"))
            return "YVR-AIrport";
        else
            return "Waterfront";
    }

    //calculate and return the shortest route between two points
    //Note: this will be the production version method
    public static ArrayList<Path> findRoutes(Station start, Station end)
    {
        ArrayList<Path> routePaths = stageOne(start, end); // list of valid paths

        /*
            HEY
            what this shit needs to do is scan through each path, and every transfer point generate a new path

            newPath.pathStops.subList(i + 1, newPath.pathStops.size()).clear(); // removes all paths after the transfer point
        */

        for (int i = 0; i < routePaths.size(); i++) // each path in group
        {
            for (int j = 0; j < routePaths.get(i).pathStops.size(); j++)
            {
                if (routePaths.get(i).pathStops.get(j).getTransferPoint())
                {
                    //call break method
                    stageTwo(routePaths.get(i));
                    break; // get out of inner loop
                }
            }
        }

        return routePaths;
    }

    //STAGE ONE METHOD FOR ROUTE GENERATION
    // In the production version, this method needs to be modified to return an ArrayList of valid paths
    public static ArrayList<Path> stageOne(Station start, Station end)
    {
        //Station start = DataProcessor.findStation(MainActivity.masterList, "KGG");  // Actual Start
        //Station end = DataProcessor.findStation(MainActivity.masterList, "NWM");    // Actual End
        ArrayList<Path> pathGroup = new ArrayList<Path>();

        //generate a path for each connector in the start station
        for (int i = 0; i < start.connectingStations.size(); i++)
        {
            String code = start.connectingStations.get(i).split("-")[0];
            Station stn = DataProcessor.findStation(MainActivity.masterList,code);

            Path test = new Path(stn, end);
            test.setPreviousStn(start);             // ensures the path doesn't go back to the start
            test.traversed.add(start.getCode());

            if (test.traverse(stn))
            {
                test.pathStops.add(start);              // add the start to the path
                Collections.reverse(test.pathStops);    // then reverse to proper order

                if (!pathGroup.contains(test))
                {
                    pathGroup.add(test);    // this is what needs to be returned
                }
            }
        }

        return pathGroup;
    }

    //STAGE TWO METHOD FOR ROUTE GENERATION
    //Takes in a valid Path, and returns an arrayList containing any additional paths that can be used
    public static ArrayList<Path> stageTwo(Path thePath)
    {
        ArrayList<Path> additionalPaths = new ArrayList<Path>();


        return additionalPaths;
    }
}
