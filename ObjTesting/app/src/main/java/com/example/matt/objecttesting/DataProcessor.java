package com.example.matt.objecttesting;

import java.util.ArrayList;

/**
 * Created by Matt on 17/02/2017.
 * This class should contain all the data processing functions
 */

public class DataProcessor
{
    //finds and returns a Station object matching the argument name
    public Station findStation(ArrayList<Station> searchArr,String searchStn)
    {
        Station result = new Station("Error", "ERR", 0);

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

    //calculate and return the shortest route between two points
    public Path findRoute(Station start, Station end)
    {
        Path result = new Path(start, end);

        //BLAH BLAH BLAH FREAKY CODE ALGORITHMS GO HERE

        return result;
    }
}
