package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.activities.MainActivity;
import com.affinitymapper.affinitymapper.activities.MapsActivity;
import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.MatchingPersonList;
import com.google.android.gms.plus.Plus;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class GetNearByUsersCall extends AffinityRepository {


    public GetNearByUsersCall(View view, Activity activity) {
        super(view, activity);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    public HttpUriRequest createRequest(Object... params) {
        return new HttpGet(URL_BASE + "getNearByUsers/" + "112584951655147133556/Health");
    }

    @Override
    public BaseModel parseResponse(HttpResponse response) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        System.out.println(total.toString());
        return gson.fromJson(total.toString(), MatchingPersonList.class);
    }

    @Override
    public boolean runAfterSuccessfulCall(BaseModel result) {
        MatchingPersonList personList = (MatchingPersonList) result;

        Intent mapIntent = new Intent(this.parentActivity, MapsActivity.class);
        mapIntent.putExtra("personList", personList);
        this.parentActivity.startActivity(mapIntent);

        return true;
    }
}
