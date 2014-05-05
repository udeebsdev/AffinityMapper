package com.affinitymapper.affinitymapper.repository.restCalls;

import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
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
public class UpdateUser extends AffinityRepository {


    public UpdateUser(View view) {
        super(view);
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
        return gson.fromJson(
                new InputStreamReader(response.getEntity().getContent()),
                Person.class);
    }

    @Override
    public boolean runAfterSuccessfulCall(BaseModel result) {
        Person person = (Person)result;
        TextView addressView = (TextView) this.passedView.findViewById(R.id.sampleText);
        System.out.println(person.getEmail());
        System.out.println(this.passedView != null ? true : false);
        System.out.println(addressView != null ? true : false);
        addressView.setText(person.getName());

        return true;
    }
}
