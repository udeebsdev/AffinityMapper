package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.activities.RegistrationActivity;
import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.Person;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class GetUserCall extends AffinityRepository {


    public GetUserCall(View view, Activity activity) {
        super(view, activity);
    }

    public GetUserCall(View view) {
        super(view);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    public HttpUriRequest createRequest(Object... params) {
        return new HttpGet(URL_BASE + "user/" + params[0]);
    }

    @Override
    public BaseModel parseResponse(HttpResponse response) throws IOException {
        return gson.fromJson(
                new InputStreamReader(response.getEntity().getContent()),
                Person.class);
    }

    @Override
    public boolean runAfterSuccessfulCall(BaseModel result) {
        ((RegistrationActivity) this.parentActivity).valuesLoaded((Person) result);
        return true;
    }
}
