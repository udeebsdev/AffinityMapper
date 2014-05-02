package com.affinitymapper.affinitymapper.repository.restCalls;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.BaseModel;
import com.affinitymapper.affinitymapper.model.Person;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

/**
 * Created by udeebsdev on 5/1/14.
 */
public abstract class AffinityRepository extends AsyncTask<String, Void, BaseModel> {

   protected String URL_BASE = "https://teamflyte-affinitymapper.appspot.com/_ah/api/affinitymapper/v1/";

    protected View passedView;

    protected Gson gson = new Gson();

    public AffinityRepository(View view) {
        this.passedView = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected BaseModel doInBackground(String... params) {
        try {
            AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = createRequest(params);
            System.out.println("Url is => " + request.getURI());

            System.out.println("Executing Rest call for =>" + request.getMethod());
            HttpResponse response = client.execute(request);
            System.out.println("Rest call status code is =>" + response.getStatusLine().getStatusCode());
            BaseModel baseModel = parseResponse(response);
            System.out.println("Response parsed to =>" + baseModel.getClass());
            client.close();
            return baseModel;
        } catch (Exception ex) {
            Log.w("GetContactTask", "Error getting contact", ex);
        }
        return null;
    }

    public abstract HttpUriRequest createRequest(String... params);

    public abstract BaseModel parseResponse(HttpResponse response) throws IOException;

    public abstract boolean runAfterSuccessfulCall(BaseModel result);

    @Override
    protected void onPostExecute(BaseModel result){
        super.onPostExecute(result);
        System.out.println("Post execute running on =>" + result.getClass());

        System.out.println("Post execute finished. Status is =>" + runAfterSuccessfulCall(result));
    };
}