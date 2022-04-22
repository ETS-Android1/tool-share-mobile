package com.toolsharemobile.myapplication;


import org.junit.Test;

import com.toolsharemobile.myapplication.activity.FindToolActivity;
import com.toolsharemobile.myapplication.utility.DistanceCalculation;

public class DistanceCalculationTest {


    @Test
    public void testSameLocationCorrectDistanceInMilesIsZero() {

        double lat1 = 461.0810160146793;
        double lon1 = -123.0867533;

        double lat2 = 461.0810160146793;
        double lon2 = -123.0867533;

        double distanceCalculation = DistanceCalculation.latLongDist(lat1, lon1, lat2, lon2);

        assert (distanceCalculation == 0);

    }


    @Test
    public void testCorrectDistanceInMilesBetweenTwoUsers() {

        double lat1 = 44.0520683;
        double lon1 = -123.0867533;

        double lat2 = 37.421998333333335;
        double lon2 = -122.084;


        double distanceCalculation = DistanceCalculation.latLongDist(lat1, lon1, lat2, lon2);

        assert (distanceCalculation == 461.0810160146793);
    }


    @Test
    public void testCorrectDistanceInMilesBetweenTwoUsersHighAccuracy() {

        double lat1 = 44.0520683;
        double lon1 = -123.0867533;

        double lat2 = 44.420683;
        double lon2 = -123.0467533;


        double distanceCalculation = DistanceCalculation.latLongDist(lat1, lon1, lat2, lon2);

        assert (distanceCalculation == 25.545672454252465);
    }
}
