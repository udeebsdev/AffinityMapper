package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.model.MatchingPerson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProfileActivity extends Activity {

    protected MatchingPerson currentPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.profile_view);

        this.currentPerson = (MatchingPerson) getIntent().getSerializableExtra("matchingPerson");

        //TODO: remove mock data
//        MatchingPerson p = new MatchingPerson();
//        p.setName("Prabina Shrestha");
//        p.setImageUrl("https://lh6.googleusercontent.com/-QHb2of9MIYM/AAAAAAAAAAI/AAAAAAAAIDg/233bTCaj-do/photo.jpg?sz=50");
//        p.setEmail("prabinashrestha@gmail.com");
//        ArrayList<String> interestGroups = new ArrayList<String>();
//        interestGroups.add("Coffee");
//        interestGroups.add("Soccer");
//        p.setInterestGroups(interestGroups);

//        this.currentPerson = p;

        this.renderProfile();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                this.currentPerson = (MatchingPerson) data.getSerializableExtra("matchingPerson");
                this.renderProfile();
                setResult(RESULT_OK, data);
            }
        }
    }

    public void renderProfile(){
        ImageView imageView = (ImageView)findViewById(R.id.userProfilePic);
        String imageUrl  = this.currentPerson.getImageUrl().substring(0,
                this.currentPerson.getImageUrl().length() - 2)
                + 400;
        URL url;

        try {
            url = new URL(imageUrl);
            new UpdateImageAsyncTask(imageView).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        TextView nameView = (TextView)this.findViewById(R.id.nameEdit);
        nameView.setText(this.currentPerson.getName());

        TextView proximityView = (TextView)this.findViewById(R.id.proximityView);
        proximityView.setText(this.currentPerson.getProximityAlertLimit()+ " miles");

        TextView interestView = (TextView)this.findViewById(R.id.interestView);
        interestView.setText(this.currentPerson.getInterestGroups().toString());

        TextView emailView = (TextView)this.findViewById(R.id.emailLabel);
        emailView.setText(this.currentPerson.getEmail());
    }

    private void updateImageView(Activity activity) {
        try {
            ImageView view = (ImageView)activity.findViewById(R.id.userProfilePic);
            URL url = new URL(((ProfileActivity)activity).currentPerson.getImageUrl());
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            view.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class UpdateImageAsyncTask extends AsyncTask<URL, Void, Bitmap>{

        ImageView imageView;

        public UpdateImageAsyncTask(ImageView iv){
            imageView = iv;
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {

            Bitmap networkBitmap = null;

            URL networkUrl = urls[0];
            try {
                networkBitmap = BitmapFactory.decodeStream(
                        networkUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return networkBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

    }

}

