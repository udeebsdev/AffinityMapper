package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.Person;
import com.affinitymapper.affinitymapper.repository.restCalls.AddUser;
import com.affinitymapper.affinitymapper.repository.restCalls.GetUserCall;
import com.affinitymapper.affinitymapper.repository.restCalls.UpdateUser;
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jawitthuhn on 5/5/2014.
 */
public class RegistrationActivity extends Activity {
    private static final int RC_INT_ACTIVITY =14;
    public boolean updateExisting = false;
    private Person currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.registration_1);

        String action = getIntent().getStringExtra("action");
        this.updateExisting = "update".equals(action);

        NumberPicker np = (NumberPicker) findViewById(R.id.proximityPicker);
        np.setMinValue(1);
        np.setMaxValue(100);
        np.setValue(3);

        if (this.updateExisting) {
            new GetUserCall(null, this).execute(getIntent().getStringExtra("userId"));
        }
    }

    public void valuesLoaded(Person person) {
        ((EditText) findViewById(R.id.displayNameEdit)).setText(person.getName());
        ((NumberPicker) findViewById(R.id.proximityPicker)).setValue(person.getProximityAlertLimit());
        ((CheckBox) findViewById(R.id.proximityCheckbox)).setChecked(person.isProximityAlertToggle());
        ((CheckBox) findViewById(R.id.chatRequestCheckbox)).setChecked(person.isChatRequestToggle());

        ArrayList<String> interests = person.getInterestGroups();
        if(interests.contains("Sports")){
            ((CheckBox)findViewById(R.id.sportsCheckBox)).setChecked(true);
        }
        if(interests.contains("Literature")){
            ((CheckBox)findViewById(R.id.literatureCheckBox)).setChecked(true);
        }
        if(interests.contains("History")){
            ((CheckBox)findViewById(R.id.historyCheckBox)).setChecked(true);
        }
        if(interests.contains("Politics")){
            ((CheckBox)findViewById(R.id.politicsCheckBox)).setChecked(true);
        }
        if(interests.contains("Health")){
            ((CheckBox)findViewById(R.id.healthCheckBox)).setChecked(true);
        }
        if(interests.contains("Movies")){
            ((CheckBox)findViewById(R.id.moviesCheckBox)).setChecked(true);
        }
        if(interests.contains("Technology")){
            ((CheckBox)findViewById(R.id.technologyCheckBox)).setChecked(true);
        }
        if(interests.contains("Food")){
            ((CheckBox)findViewById(R.id.foodCheckBox)).setChecked(true);
        }

    }

    public void registrationFormSubmit(View view) {
        Person person = new Person();
        person.setUserId(getIntent().getStringExtra("userId"));
        person.setEmail(getIntent().getStringExtra("email"));
        person.setImageUrl(getIntent().getStringExtra("imageUrl"));

        String name = ((EditText) findViewById(R.id.displayNameEdit)).getText().toString();
        person.setName(name);

        int proximity = ((NumberPicker) findViewById(R.id.proximityPicker)).getValue();
        person.setProximityAlertLimit(proximity);

        boolean proximityNotify = ((CheckBox) findViewById(R.id.proximityCheckbox)).isChecked();
        person.setProximityAlertToggle(proximityNotify);

        boolean chatNotify = ((CheckBox) findViewById(R.id.chatRequestCheckbox)).isChecked();
        person.setChatRequestToggle(chatNotify);

        ArrayList<String> interestGroups = new ArrayList<String>();
        if(((CheckBox)findViewById(R.id.sportsCheckBox)).isChecked())
        {
            interestGroups.add("Sports");
        }
        if(((CheckBox)findViewById(R.id.literatureCheckBox)).isChecked())
        {
            interestGroups.add("Literature");
        }
        if(((CheckBox)findViewById(R.id.historyCheckBox)).isChecked())
        {
            interestGroups.add("History");
        }
        if(((CheckBox)findViewById(R.id.politicsCheckBox)).isChecked())
        {
            interestGroups.add("Politics");
        }
        if(((CheckBox)findViewById(R.id.healthCheckBox)).isChecked())
        {
            interestGroups.add("Health");
        }
        if(((CheckBox)findViewById(R.id.moviesCheckBox)).isChecked())
        {
            interestGroups.add("Movies");
        }
        if(((CheckBox)findViewById(R.id.technologyCheckBox)).isChecked())
        {
            interestGroups.add("Technology");
        }
        if(((CheckBox)findViewById(R.id.foodCheckBox)).isChecked())
        {
            interestGroups.add("Food");
        }


        person.setInterestGroups(interestGroups);

        this.currentUser = person;

        if (this.updateExisting) {
            new UpdateUser(view.getRootView(), this).execute(person);
        } else {
            new AddUser(view.getRootView(), this).execute(person);
        }
    }

    public boolean userRegistrationComplete()
    {
        if (!this.updateExisting) {
            Toast.makeText(getApplicationContext(), "Registration Completed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Profile Updated.", Toast.LENGTH_SHORT).show();
        }

        Intent mainIntent = new Intent(this, Interests.class);
        mainIntent.putExtra("email", getIntent().getStringExtra("email"));
        mainIntent.putExtra("userId", getIntent().getStringExtra("userId"));
        mainIntent.putExtra("imageUrl", getIntent().getStringExtra("imageUrl"));
        mainIntent.putExtra("person", this.currentUser);

        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        this.startActivityForResult(mainIntent, RC_INT_ACTIVITY);

        return true;
    }
}
