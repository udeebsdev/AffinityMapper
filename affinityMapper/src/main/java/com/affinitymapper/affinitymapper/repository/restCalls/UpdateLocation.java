package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.UserLocation;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class UpdateLocation extends AffinityRepository {


    public UpdateLocation(View view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    public HttpUriRequest createRequest(Object... params) throws IOException {
        HttpPost uriRequest = new HttpPost(URL_BASE + "location/user/" + "udeeb");

        System.out.println(gson.toJson((UserLocation) params[0]));
        StringEntity se = new StringEntity(gson.toJson((UserLocation) params[0]));
        uriRequest.setEntity(se);
        return uriRequest;
    }

    @Override
    public BaseModel parseResponse(HttpResponse response) throws IOException {

//        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//        StringBuilder total = new StringBuilder();
//        String line;
//        while ((line = r.readLine()) != null) {
//            total.append(line);
//        }
//        System.out.println(total.toString());

        return null;
    }

    @Override
    public boolean runAfterSuccessfulCall(BaseModel result) {
//        Person person = (Person)result;
//        TextView addressView = (TextView) this.currentView.findViewById(R.id.sampleText);
//        System.out.println(person.getUserId());
//        System.out.println(this.currentView != null ? true : false);
//        System.out.println(addressView != null ? true : false);
//        addressView.setText(person.getName());
        Toast.makeText(this.parentActivity.getBaseContext(), "Your current location has been updated.", Toast.LENGTH_SHORT);
        return true;
    }
}
