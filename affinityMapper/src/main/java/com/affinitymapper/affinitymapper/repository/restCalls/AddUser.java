package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.activities.RegistrationActivity;
import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.Person;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class AddUser extends AffinityRepository {


    public AddUser(View view, Activity activity) {
        super(view, activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    public HttpUriRequest createRequest(Object... params) throws IOException {
        HttpPost uriRequest= new HttpPost(URL_BASE + "user/add");
        System.out.println(gson.toJson((Person) params[0]));
        StringEntity se = new StringEntity(gson.toJson((Person) params[0]));
        uriRequest.setEntity(se);
        uriRequest.setHeader("Accept", "application/json");
        uriRequest.setHeader("Content-type", "application/json");
        return uriRequest;
    }

    @Override
    public BaseModel parseResponse(HttpResponse response) throws IOException {
        return null;
    }

    @Override
    public boolean runAfterSuccessfulCall(BaseModel result) {
        ((RegistrationActivity) this.parentActivity).userRegistrationComplete();
        return true;
    }
}
