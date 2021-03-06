package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.Utilities.LocationUtilities;
import com.affinitymapper.affinitymapper.model.UserLocation;
import com.affinitymapper.affinitymapper.repository.restCalls.GetNearByUsersCall;
import com.affinitymapper.affinitymapper.repository.restCalls.UpdateLocation;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String email = (String) getIntent().getSerializableExtra("email");
        TextView sampleText = (TextView) this.findViewById(R.id.sampleText);
        sampleText.setText("Logged in as " + email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void updateLocationClicked(View view) {
        System.out.println("Button Clicked " + view.getId());
        LocationUtilities locationHelper = LocationUtilities.getLocationUtilities(this);
        Location currentLocation = locationHelper.getCurrentLocation();

        UserLocation userLocation = new UserLocation();
        userLocation.setLatitude(currentLocation.getLatitude());
        userLocation.setLongitude(currentLocation.getLongitude());
        userLocation.setActive(true);
        System.out.println("Last known Users Location to be updated is => \n Lat : "+ currentLocation.getLatitude() +" Lon is : "+currentLocation.getLongitude());
        //TODO populated the user id here
        //userLocation.setUserId();
        new UpdateLocation(view.getRootView(), this).execute(userLocation);
    }

    public void sampleButtonClicked(View view) {
        System.out.println("Button Clicked " + view.getId());
        new GetNearByUsersCall(view.getRootView(), this).execute((String) getIntent().getSerializableExtra("userId"));
    }

    public void signOutButtonClicked(View view) {
        System.out.println("Button Clicked " + view.getId());
        setResult(12, getIntent());
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
