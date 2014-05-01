package com.affinitymapper.affinitymapper.repository.restCalls;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.Person;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.InputStreamReader;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class GetUser extends AsyncTask<String, Void, Person> {

    String URL_BASE = "https://teamflyte-affinitymapper.appspot.com/_ah/api/affinitymapper/v1/user/";

    private View passedView;

    public GetUser(View view) {
        this.passedView = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Person doInBackground(String... params) {
        String contactId = params[0];
        System.out.println(params);
        try {
            AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = new HttpGet(URL_BASE +
                    contactId);
            HttpResponse response = client.execute(request);
            Gson gson = new Gson();
            //System.out.println(response.getEntity().getContent());
            Person result = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    Person.class);
            client.close();
            return result;
        } catch (Exception ex) {
            Log.w("GetContactTask", "Error getting contact", ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Person result) {
        super.onPostExecute(result);
        TextView addressView = (TextView) this.passedView.findViewById(R.id.sampleText);
        System.out.println(result.getEmail());
        System.out.println(this.passedView != null ? true : false);
        System.out.println(addressView != null ? true : false);
        addressView.setText(result.getName());
// close the progress dialog and tell the activity that you've received a result 
    }
}
