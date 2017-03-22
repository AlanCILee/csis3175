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
            return "YVR-Airport";
        else
            return "Waterfront";
    }

    //calculate and return the shortest route between two points
    //Note: this will be the production version method
    public static ArrayList<Path> findRoutes(Station start, Station end)
    {
        ArrayList<Path> routePaths = stageOne(start, end); // list of valid paths

        for (int p = 0; p < routePaths.size(); p++)
        {
            System.out.println("FIRST GEN, PATH OF SIZE " + routePaths.get(p).pathStops.size() + " ADDED");
        }

        ArrayList<Path> additionalPaths = new ArrayList<Path>();
        /*
            HEY
            what this shit needs to do is scan through each path, and every transfer point generate a new path

            newPath.pathStops.subList(i + 1, newPath.pathStops.size()).clear(); // removes all paths after the transfer point
        */

        for (int i = 0; i < routePaths.size(); i++) // each path in group
        {
            for (int j = 0; j < routePaths.get(i).pathStops.size(); j++) // each stop in path
            {
                if (routePaths.get(i).pathStops.get(j).getTransferPoint())
                {
                    //call break method
                    Path newPath = new Path(routePaths.get(i));
                    //System.out.println(newPath.pathStops.size());
                    newPath.pathStops.subList(j + 1, newPath.pathStops.size()).clear();

                    String code = newPath.pathStops.get(newPath.pathStops.size() - 1).getCode();
                    ArrayList<Path> more = stageOne(DataProcessor.findStation(MainActivity.masterList, code), end);

                    //System.out.println(newPath.pathStops.size());

                    for (int k = 0; k < more.size(); k++)
                    {
                        more.get(k).insertPaths(newPath);

                        if (more.get(k).pathStops.get(0).getCode().equals(start.getCode()) && more.get(k).pathStops.get(more.get(k).pathStops.size() - 1).getCode().equals(end.getCode()) && !dupePath(routePaths, more.get(k)))
                        {
                            additionalPaths.add(more.get(k));
                        }
                    }

                    break; // get out of inner loop
                }
            }
        }

        for (int z = 0; z < additionalPaths.size(); z++)
        {
            routePaths.add(additionalPaths.get(z));
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

    //checks for duplicate paths
    private static boolean dupePath(ArrayList<Path> checkList, Path checking)
    {
        boolean dupe = true;

        for (int i = 0; i < checkList.size(); i++)
        {
            if (checkList.get(i).pathStops.size() != checking.pathStops.size())
            {
                for (int j = 0; j < checkList.get(i).pathStops.size(); j++)
                {
                    if (!checkList.get(i).pathStops.get(j).getCode().equals(checking.pathStops.get(j).getCode()))
                    {
                        return false;
                    }
                }
            }
        }

        return dupe;
    }
}
