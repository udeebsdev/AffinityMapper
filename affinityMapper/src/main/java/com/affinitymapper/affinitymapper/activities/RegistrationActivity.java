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
import com.google.android.gms.plus.Plus;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jawitthuhn on 5/5/2014.
 */
public class RegistrationActivity extends Activity {
    private static final int RC_INT_ACTIVITY =14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_1);

        NumberPicker np = (NumberPicker) findViewById(R.id.proximityPicker);
        np.setMinValue(1);
        np.setMaxValue(100);
        np.setValue(3);
    }

    public void registrationFormSubmit(View view) {
        Person person = new Person();
        person.setUserId(getIntent().getStringExtra("userId"));
        person.setEmail(getIntent().getStringExtra("email"));
        person.setImageUrl(getIntent().getStringExtra("imageUrl"));

        String name = ((EditText) findViewById(R.id.displayNameEdit)).getText().toString();
        person.setName(name);

        String group = ((Spinner) findViewById(R.id.interestGroupsSpinner)).getSelectedItem().toString();
        ArrayList<String> interestGroups = new ArrayList<String>();
        interestGroups.add(group);
        person.setInterestGroups(interestGroups);

        int proximity = ((NumberPicker) findViewById(R.id.proximityPicker)).getValue();
        person.setProximityAlertLimit(proximity);

        boolean proximityNotify = ((CheckBox) findViewById(R.id.proximityCheckbox)).isChecked();
        person.setProximityAlertToggle(proximityNotify);

        boolean chatNotify = ((CheckBox) findViewById(R.id.chatRequestCheckbox)).isChecked();
        person.setChatRequestToggle(chatNotify);

        new AddUser(view.getRootView(), this).execute(person);
    }

    public boolean userRegistrationComplete()
    {
        Toast.makeText(getApplicationContext(), "Registration Completed.", Toast.LENGTH_SHORT).show();

        Intent mainIntent = new Intent(this, Interests.class);
        mainIntent.putExtra("email", getIntent().getStringExtra("email"));
        mainIntent.putExtra("userId", getIntent().getStringExtra("userId"));
        mainIntent.putExtra("imageUrl", getIntent().getStringExtra("imageUrl"));
        this.startActivityForResult(mainIntent, RC_INT_ACTIVITY);

        return true;
    }
}
