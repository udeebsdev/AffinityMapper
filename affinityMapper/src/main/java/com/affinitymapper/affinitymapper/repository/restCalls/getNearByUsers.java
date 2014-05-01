package com.affinitymapper.affinitymapper.repository.restCalls;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.MatchingPersonList;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.InputStreamReader;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class GetNearByUsers extends AsyncTask<String, Void, MatchingPersonList> {

    String URL_BASE = "https://teamflyte-affinitymapper.appspot.com/_ah/api/affinitymapper/v1/getNearByUsers/";

    private View passedView;

    public GetNearByUsers(View view) {
        this.passedView = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected MatchingPersonList doInBackground(String... params) {
        String contactId = params[0];
        try {
            AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = new HttpGet(URL_BASE +
                    contactId);
            HttpResponse response = client.execute(request);
            Gson gson = new Gson();
            MatchingPersonList result = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    MatchingPersonList.class);
            client.close();
            return result;
        } catch (Exception ex) {
            Log.w("GetContactTask", "Error getting contact", ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(MatchingPersonList result) {
        super.onPostExecute(result);
        TextView addressView = (TextView) this.passedView.findViewById(R.id.sampleText);
        addressView.setText("something");
    }
}
