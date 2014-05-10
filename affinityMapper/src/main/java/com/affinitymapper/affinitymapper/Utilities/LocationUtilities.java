package com.affinitymapper.affinitymapper.Utilities;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by udeebsdev on 5/6/14.
 */
public class LocationUtilities {

    private static LocationUtilities locationHelper;

    private Activity activity;

    private Location currentLocation;

    public static LocationUtilities getLocationUtilities(Activity activity) {
        if (locationHelper != null) {
            return locationHelper;
        } else {
            locationHelper = new LocationUtilities(activity);
            return locationHelper;
        }
    }

    private LocationUtilities(Activity activity) {
        this.activity = activity;
        this._getLocation();
    }

    private void _getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager)
                this.activity.getSystemService(this.activity.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Log.w("LocationUtilities", "Current best provider is => " + bestProvider);
        if (bestProvider == null) {
            Toast.makeText(this.activity.getApplicationContext(), "Please enable location services!!", Toast.LENGTH_LONG);
        }
        currentLocation = locationManager.getLastKnownLocation(bestProvider);

        if (currentLocation != null) {
            System.out.println("Last known Users Location 1 is => Lat : " + currentLocation.getLatitude() + " Lon is : " + currentLocation.getLongitude());
        } else {
            System.out.println("Cannot lock on last know location at this point 1");
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
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
        if (currentLocation != null) {
            System.out.println("Last known Users Location 2 is => Lat : " + currentLocation.getLatitude() + " Lon is : " + currentLocation.getLongitude());
        } else {
            System.out.println("Cannot lock on last know location at this point 2");
            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}
