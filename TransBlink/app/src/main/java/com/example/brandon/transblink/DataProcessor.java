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
    // IMPORTANT: This method is only to be used internally in the program as it relies on the assumtion
    // that a valid search result exists. DO NOT USE FOR USER SEARCHING
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
                    return result;
                }
            }
            else
            {
                if (searchStn.compareTo(searchArr.get(i).getCode()) == 0)
                {
                    result = searchArr.get(i);
                    return result;
                }
            }
        }

        return result;
    }

    //calculate and return the shortest route between two points
    //Note: this will be the production version method
    public static ArrayList<Path> findRoutes(Station start, Station end)
    {
        ArrayList<Path> routePaths = stageOne(start, end); // list of valid paths
        System.out.println("FIRST GENERATION, " + routePaths.size() + " PATHS CREATED");
        System.out.println("PATH FROM " + start.getFullName() + " TO " + end.getFullName());

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
                    //System.out.println("TPOINT AT " + routePaths.get(i).pathStops.get(j).getCode());

                    //call break method
                    Path newPath = new Path(routePaths.get(i)); //second half
                    //System.out.println(newPath.pathStops.size());
                    newPath.pathStops.subList(j + 1, newPath.pathStops.size()).clear();

                    String code = newPath.pathStops.get(newPath.pathStops.size() - 1).getCode();
                    ArrayList<Path> more = stageOne(DataProcessor.findStation(MainActivity.masterList, code), end); //first half

                    //System.out.println(newPath.pathStops.size());

                    for (int k = 0; k < more.size(); k++)
                    {
                        more.get(k).insertPaths(newPath); //adding two

                        if (more.get(k).pathStops.get(0).getCode().equals(start.getCode()) && more.get(k).pathStops.get(more.get(k).pathStops.size() - 1).getCode().equals(end.getCode()))
                        {
                            if (!dupePath(routePaths, more.get(k)) && integrityCheck(more.get(k)))
                            {
                                if (additionalPaths.size() == 0)
                                {
                                    additionalPaths.add(more.get(k));
                                }
                                else
                                {
                                    if (!dupePath(additionalPaths, more.get(k)))
                                    {
                                        additionalPaths.add(more.get(k));
                                    }
                                }
                            }
                        }
                    }

                    //break; // get out of inner loop
                }
            }
        }

        System.out.println("SECOND GENERATION, " + additionalPaths.size() + " PATHS CREATED");

        for (int z = 0; z < additionalPaths.size(); z++)
        {
            routePaths.add(additionalPaths.get(z));
        }

        // This loop sets the data for each path
        for (int z = 0; z < routePaths.size(); z++)
        {
            setData(routePaths.get(z));
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

                if (!pathGroup.contains(test) && !dupePath(pathGroup, test))
                {
                    pathGroup.add(test);    // this is what needs to be returned
                }
            }
        }

        return pathGroup;
    }

    //checks two paths to see if their stops are the same
    //returns true if there is a duplicate
    private static boolean dupePath(ArrayList<Path> checklist, Path checkPath)
    {
        boolean dupe = false;

        for (int i = 0; i < checklist.size(); i++) // each path in the list
        {
            Path temp = checklist.get(i);

            if (temp.pathStops.size() == checkPath.pathStops.size())
            {
                for (int j = 0; j < temp.pathStops.size(); j++) // each station in the path
                {
                    if (temp.pathStops.get(j).getCode().compareToIgnoreCase(checkPath.pathStops.get(j).getCode()) != 0)
                    {
                        dupe = false;
                    }
                    else
                    {
                        dupe = true;
                        break;
                    }
                }
            }

            if (dupe)
                break;
        }

        return dupe;
    }

    // Integrity Check confirms that the Stations in the path are infact connected to each other
    // returns false if a teleport is detected
    private static boolean integrityCheck(Path checkPath)
    {
        boolean valid = true;
        int last = checkPath.pathStops.size() - 1;

        //System.out.println("IN INTEGERITY CHECK");

        //if (!checkPath.pathStops.get(0).getCode().equals(checkPath.getStartCode()) || !checkPath.pathStops.get(last).getCode().equals(checkPath.getEndCode()))
            //return false;

        for (int i = 0; i < last; i++)
        {
            String next = checkPath.pathStops.get(i+1).getCode();
            boolean control = false;

            for (int j = 0; j < checkPath.pathStops.get(i).connectingStations.size(); j++)
            {
                //System.out.println("COMPARING " + checkPath.pathStops.get(i).connectingStations.get(j).split("-")[0] + " WITH " + next);

                if (checkPath.pathStops.get(i).connectingStations.get(j).split("-")[0].equals(next))
                {
                    control = true;
                    break;
                }
            }

            if (!control)
                return false;
        }

        return valid;
    }

    //sets the data for each path; call this shit in a loop
    // Matt (to Alan): if you're making a method to set the transfer data, shove it in this method kthnx
    private static void setData(Path thePath)
    {
        thePath.setNumStops();

    }
}
