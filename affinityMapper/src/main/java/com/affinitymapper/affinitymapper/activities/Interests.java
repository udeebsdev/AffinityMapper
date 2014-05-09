package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.Utilities.LocationUtilities;
import com.affinitymapper.affinitymapper.model.Person;
import com.affinitymapper.affinitymapper.model.UserLocation;
import com.affinitymapper.affinitymapper.repository.restCalls.GetNearByUsersCall;
import com.affinitymapper.affinitymapper.repository.restCalls.UpdateLocation;

import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.TabActivity;
import android.widget.TabHost.OnTabChangeListener;

import java.util.ArrayList;

public class Interests extends Activity {

    private static final int RC_MAP_ACTIVITY =15;
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);

        person = (Person) getIntent().getSerializableExtra("person");

        /*String userId = ((String) getIntent().getSerializableExtra("userId"));
        String email = ((String) getIntent().getSerializableExtra("email"));
        String imageUrl = ((String) getIntent().getSerializableExtra("imageUrl"));

        person = new Person();
        person.setUserId(userId);
        person.setEmail(email);
        person.setImageUrl(imageUrl);
        ArrayList<String> interestGroups = new ArrayList<String>();
        interestGroups.add("Sport");
        //interestGroups.add("History");
        //interestGroups.add("Literature");
        interestGroups.add("Politics");
        interestGroups.add("Movies");
        interestGroups.add("Health");
        interestGroups.add("Technology");
        interestGroups.add("Food");
        person.setInterestGroups(interestGroups);*/

        updateLocation();

        updateUI();
    }

    private void updateUI(){
        //TODO add support for remaining interests
        ((ImageButton) findViewById(R.id.sportsButton)).setVisibility(View.GONE);
        ((ImageButton) findViewById(R.id.historyButton)).setVisibility(View.GONE);
        ((ImageButton) findViewById(R.id.literatureButton)).setVisibility(View.GONE);
        ((ImageButton) findViewById(R.id.politicsButton)).setVisibility(View.GONE);


        for (String interest : person.getInterestGroups()){
            if("Sport".equalsIgnoreCase(interest)){
                ((ImageButton) findViewById(R.id.sportsButton)).setVisibility(View.VISIBLE);
            }
            else if("History".equalsIgnoreCase(interest)){
                ((ImageButton) findViewById(R.id.historyButton)).setVisibility(View.VISIBLE);
            }
            else if("Literature".equalsIgnoreCase(interest)){
                ((ImageButton) findViewById(R.id.literatureButton)).setVisibility(View.VISIBLE);
            }
            else if("Politics".equalsIgnoreCase(interest)){
                ((ImageButton) findViewById(R.id.politicsButton)).setVisibility(View.VISIBLE);
            }
        }
    }

    public void updateLocation() {
        LocationUtilities locationHelper = LocationUtilities.getLocationUtilities(this);
        Location currentLocation = locationHelper.getCurrentLocation();

        UserLocation userLocation = new UserLocation();
        userLocation.setLatitude(currentLocation.getLatitude());
        userLocation.setLongitude(currentLocation.getLongitude());
        userLocation.setActive(true);
        System.out.println("Last known Users Location to be updated is => \n Lat : "+ currentLocation.getLatitude() +" Lon is : "+currentLocation.getLongitude());
        userLocation.setUserId(person.getUserId());
        new UpdateLocation(null, this).execute(userLocation);
    }

    public void sportsClicked(View view)
    {
        Toast.makeText(getApplicationContext(), "Sports button is clicked", Toast.LENGTH_LONG).show();
        launchMaps(view, "Sport");

    }

    public void literatureClick(View view)
    {
        Toast.makeText(getApplicationContext(), "Literature button is clicked", Toast.LENGTH_LONG).show();
        launchMaps(view, "Literature");
    }

    public void historyClick(View view)
    {
        Toast.makeText(getApplicationContext(), "History button is clicked", Toast.LENGTH_LONG).show();
        launchMaps(view, "History");
    }

    public void PoliticsClick(View view)
    {
        Toast.makeText(getApplicationContext(), "Politics button is clicked", Toast.LENGTH_LONG).show();
        launchMaps(view, "Politics");
    }

    public void launchMaps(View view, String interest){
        Intent registrationIntent = new Intent(this, MapsActivity.class);
        registrationIntent.putExtra("person", person);
        registrationIntent.putExtra("interest", interest);
        this.startActivityForResult(registrationIntent, RC_MAP_ACTIVITY);
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
