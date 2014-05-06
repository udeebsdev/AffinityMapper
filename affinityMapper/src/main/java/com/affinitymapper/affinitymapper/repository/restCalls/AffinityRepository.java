package com.affinitymapper.affinitymapper.repository.restCalls;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.affinitymapper.affinitymapper.model.BaseModel;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

/**
 * Created by udeebsdev on 5/1/14.
 */
public abstract class AffinityRepository extends AsyncTask<Object, Void, BaseModel> implements IAffinityRepository {

    protected String URL_BASE = "https://teamflyte-affinitymapper.appspot.com/_ah/api/affinitymapper/v1/";

    protected View currentView;

    protected Activity parentActivity;

    protected Gson gson = new Gson();

    public AffinityRepository(View view, Activity activity) {
        this.currentView = view;
        this.parentActivity = activity;
    }

    public AffinityRepository(View view) {
        this.currentView = view;
    }

    @Override
    protected void onPreExecute() {
        System.out.println("Running PreExecute... ");

        super.onPreExecute();
    }

    @Override
    public BaseModel doInBackground(Object... params) {
        try {
            AndroidHttpClient client = AndroidHttpClient.newInstance("Android", null);
            HttpUriRequest request = createRequest(params);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");

            System.out.println("Url is => " + request.getURI());

            System.out.println("Executing Rest call for =>" + request.getMethod());
            HttpResponse response = client.execute(request);
            System.out.println("Rest call status code is =>" + response.getStatusLine().getStatusCode());
            BaseModel baseModel = parseResponse(response);
            if (baseModel != null) {
                System.out.println("Response parsed to =>" + baseModel.getClass());
            } else {
                System.out.println("Response parsed to =>");
            }
            client.close();
            return baseModel;
        } catch (Exception ex) {
            Log.w("GetContactTask", "Error getting contact", ex);
        }
        return null;
    }

    public abstract HttpUriRequest createRequest(Object... params) throws IOException;

    public abstract BaseModel parseResponse(HttpResponse response) throws IOException;

    public abstract boolean runAfterSuccessfulCall(BaseModel result);

    @Override
    protected void onPostExecute(BaseModel result) {
        super.onPostExecute(result);
        if (result != null) {
            System.out.println("Post execute running on =>" + result.getClass());
        } else {
            System.out.println("Post execute running after POST");
        }

        System.out.println("Post execute finished. Status is =>" + runAfterSuccessfulCall(result));
    }

    ;
}
