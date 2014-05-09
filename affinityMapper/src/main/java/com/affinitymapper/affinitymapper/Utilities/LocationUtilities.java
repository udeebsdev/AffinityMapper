package com.affinitymapper.affinitymapper.Utilities;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by udeebsdev on 5/6/14.
 */
public class LocationUtilities {

    private static LocationUtilities locationHelper;

    private Activity activity;

    private Location currentLocation;

    public static LocationUtilities getLocationUtilities(Activity activity){
        if(locationHelper!= null){
            return locationHelper;
        }else{
            locationHelper = new LocationUtilities(activity);
            return  locationHelper;
        }
    }

    private LocationUtilities(Activity activity){
        this.activity = activity;
        this._getLocation();
    }

    private void _getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager)
                this.activity.getSystemService(this.activity.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        currentLocation = locationManager.getLastKnownLocation(bestProvider);

        System.out.println("Last known Users Location 1 is => Lat : "+ currentLocation.getLatitude() +" Lon is : "+currentLocation.getLongitude());

        LocationListener loc_listener = new LocationListener() {

            public void onLocationChanged(Location l) {
                currentLocation = l;
            }

            public void onProviderEnabled(String p) {
            }

            public void onProviderDisabled(String p) {
            }

            public void onStatusChanged(String p, int status, Bundle extras) {
            }
        };
        locationManager
                .requestLocationUpdates(bestProvider, 0, 0, loc_listener);
        currentLocation = locationManager.getLastKnownLocation(bestProvider);
        System.out.println("Last known Users Location 2 is => Lat : "+ currentLocation.getLatitude() +" Lon is : "+currentLocation.getLongitude());
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}
