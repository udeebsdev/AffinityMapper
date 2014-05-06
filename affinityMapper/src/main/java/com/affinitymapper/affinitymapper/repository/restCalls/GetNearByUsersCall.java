package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.MatchingPersonList;

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
        return new HttpGet(URL_BASE + "getNearByUsers/" + "udeeb");
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
        MatchingPersonList person = (MatchingPersonList) result;
        TextView addressView = (TextView) this.currentView.findViewById(R.id.sampleText);
        System.out.println(person.getMatchingPersons().get(0).getEmail());
        System.out.println(this.currentView != null ? true : false);
        System.out.println(addressView != null ? true : false);
        addressView.setText(person.getMatchingPersons().get(0).getEmail() + " " + person.getMatchingPersons().get(0).getLatitude());

        return true;
    }
}
