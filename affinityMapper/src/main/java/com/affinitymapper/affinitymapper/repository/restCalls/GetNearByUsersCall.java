package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.affinitymapper.affinitymapper.activities.MapsActivity;
import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.MatchingPerson;
import com.affinitymapper.affinitymapper.model.MatchingPersonList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
        System.out.println("User Id is => " + params[0]);
        System.out.println("Interest is => " + params[1]);
        return new HttpGet(URL_BASE + "getNearByUsers/" + params[0] + "/" + params[1]);
    }

    @Override
    public BaseModel parseResponse(HttpResponse response) throws IOException {
        if(response.getEntity() != null) {
            BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            System.out.println(total.toString());
            return gson.fromJson(total.toString(), MatchingPersonList.class);
        }
        else{
            System.out.println("There are no user matching your location at this time");
            return new MatchingPersonList(new ArrayList<MatchingPerson>());
        }
    }

    @Override
    public boolean runAfterSuccessfulCall(BaseModel result) {
        MatchingPersonList personList = (MatchingPersonList) result;

//        Intent mapIntent = new Intent(this.parentActivity, MapsActivity.class);
//        mapIntent.putExtra("personList", personList);
//        this.parentActivity.startActivity(mapIntent);
        ((MapsActivity)this.parentActivity).putUsersOnTheMap(personList);

        return true;
    }
}
