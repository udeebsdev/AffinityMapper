package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.UserLocation;
import com.affinitymapper.affinitymapper.model.Person;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class GetLocation extends AffinityRepository {


    public GetLocation(View view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    public HttpUriRequest createRequest(Object... params) {
        return new HttpGet(URL_BASE + "location/user/" + "udeeb");
    }

    @Override
    public BaseModel parseResponse(HttpResponse response) throws IOException {
        return gson.fromJson(
                new InputStreamReader(response.getEntity().getContent()),
                Person.class);
    }

    @Override
    public boolean runAfterSuccessfulCall(BaseModel result) {
        UserLocation person = (UserLocation)result;
        TextView addressView = (TextView) this.currentView.findViewById(R.id.sampleText);
        System.out.println(person.getEmail());
        System.out.println(this.currentView != null ? true : false);
        System.out.println(addressView != null ? true : false);
        addressView.setText(person.getLatitude().toString());

        return true;
    }
}
