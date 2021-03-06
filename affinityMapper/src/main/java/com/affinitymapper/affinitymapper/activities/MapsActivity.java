package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.widget.Toast;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.Utilities.LocationUtilities;
import com.affinitymapper.affinitymapper.model.MatchingPerson;
import com.affinitymapper.affinitymapper.model.MatchingPersonList;
import com.affinitymapper.affinitymapper.model.Person;
import com.affinitymapper.affinitymapper.repository.restCalls.GetNearByUsersCall;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Activity currentActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        final Intent currentIntent = getIntent();

        Person person = (Person) getIntent().getSerializableExtra("person");
        String interest = ((String) getIntent().getSerializableExtra("interest"));

        // Make a call to get close by users
        new GetNearByUsersCall(null, this).execute(person.getUserId(), interest);


        LocationUtilities locationHelper = LocationUtilities.getLocationUtilities(this);
        Location currentUserLocation = locationHelper.getCurrentLocation();

        MatchingPerson currentUser = new MatchingPerson();
        currentUser.setUserId(person.getUserId());
        currentUser.setLatitude(currentUserLocation.getLatitude());
        currentUser.setLongitude(currentUserLocation.getLongitude());

        //(MatchingPerson) currentIntent.getSerializableExtra("currentPerson");
        float zoomLevel = calculateZoomLevel(person.getProximityAlertLimit() == 0 ? 10 : person.getProximityAlertLimit());
        System.out.println("Current zoom level is => " + zoomLevel);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentUser.getLatitude(), currentUser.getLongitude()), zoomLevel));

//        mMap.addMarker(new MarkerOptions().position(new LatLng(currentUser.getLatitude(), currentUser.getLongitude()))
//                .title(currentUser.getName())
//                .snippet(currentUser.getEmail()));

    }

    public void putUsersOnTheMap(final MatchingPersonList personList) {
        // final MatchingPersonList personList = (MatchingPersonList) currentIntent.getSerializableExtra("personList");
        for (MatchingPerson mPerson : personList.getMatchingPersons()) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(mPerson.getLatitude(), mPerson.getLongitude()))
                    .title(mPerson.getName())
                    .snippet(mPerson.getEmail()));
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getBaseContext(), marker.getTitle(), Toast.LENGTH_SHORT);

                MatchingPerson mPerson = new MatchingPerson();
                for (MatchingPerson person : personList.getMatchingPersons()) {
                    if (person.getName().compareTo(marker.getTitle()) == 0 && person.getEmail().compareTo(marker.getSnippet()) == 0) {
                        mPerson = person;
                    }
                }

                Intent mapIntent = new Intent(currentActivity, ProfileActivity.class);
                mapIntent.putExtra("matchingPerson", mPerson);
                startActivity(mapIntent);

            }
        });
    }

    // returns an appropriate zoom level for the distance that the user has selected.
    private int calculateZoomLevel(int widthToDisplayInMiles) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        System.out.println("Current width of the screen is => " + size.x);
        double equatorLength = 40075004; // in meters
        double metersPerPixel = equatorLength / 256;
        int zoomLevel = 1;
        System.out.println("Total width to display in m is => " + widthToDisplayInMiles * 1.6 * 1000 * 2 + " in miles is => " + widthToDisplayInMiles);

        while ((metersPerPixel * size.x) > (widthToDisplayInMiles * 1.6 * 1000 * 2)) {
            System.out.println("Current meter diplayed in window is => " + metersPerPixel * size.x);
            metersPerPixel /= 2;
            ++zoomLevel;
        }
        return zoomLevel;
    }
}
