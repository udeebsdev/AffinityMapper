package com.affinitymapper.affinitymapper.repository.restCalls;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.MatchingPerson;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by udeebsdev on 4/30/14.
 */
public class GetNearByUsers extends AsyncTask<String, Void, ArrayList<MatchingPerson>> {

    String URL_BASE = "https://teamflyte-affinitymapper.appspot.com/_ah/api#p/affinitymapper/v1/affinitymapper.";

    private View passedView;

    public GetNearByUsers(View view){
        this.passedView = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<MatchingPerson> doInBackground(String... params) {
        String contactId = params[0];
        try {
            AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = new HttpGet(URL_BASE + "getNearByUsers?userId=" +
                    contactId);
            HttpResponse response = client.execute(request);
            Gson gson = new Gson();
            ArrayList<MatchingPerson> result = gson.fromJson(
                    new InputStreamReader(response.getEntity().getContent()),
                    ArrayList.class);
            client.close();
            return result;
        } catch (Exception ex) {
            Log.w("GetContactTask", "Error getting contact", ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MatchingPerson> result) {
        super.onPostExecute(result);
        TextView addressView = (TextView) this.passedView.findViewById(R.id.sampleText);
        addressView.setText("something");
// close the progress dialog and tell the activity that you've received a result 
    }
}
