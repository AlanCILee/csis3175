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
        ArrayList<Path> routePaths = new ArrayList<Path>();
        Path result = new Path(start, end);

        if (result.traverse(start))
        {
            System.out.println("SUCCESS");
            Collections.reverse(result.pathStops); //need to reverse order
            routePaths.add(result);
        }

        return routePaths;
    }

    //THIS IS A TESTING METHOD
    public static void testAlgorithm()
    {
        Station start = DataProcessor.findStation(MainActivity.masterList, "BRD");
        Station end = DataProcessor.findStation(MainActivity.masterList, "LTC");

        Path test = new Path(start, end);

        if (test.traverse(start))
        {
            System.out.println("SUCCESS");
            Collections.reverse(test.pathStops);

            for (int i = 0; i < test.pathStops.size(); i++)
            {
                System.out.println(test.pathStops.get(i).getFullName());
            }
        }
    }
}
