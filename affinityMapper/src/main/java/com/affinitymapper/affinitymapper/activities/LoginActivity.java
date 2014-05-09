package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.affinitymapper.affinitymapper.R;
import com.affinitymapper.affinitymapper.repository.restCalls.GetUserInfoAfterSignIn;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;
import java.io.InputStream;

public class LoginActivity extends Activity implements View.OnClickListener,
        ConnectionCallbacks, OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;
    private static final int RC_MAIN_ACTIVITY = 11;
    private static final int RC_SIGN_OUT = 12;
    private static final int RC_REG_ACTIVITY = 13;
    private static final int RC_INT_ACTIVITY =14;
    private static final int RC_MAP_ACTIVITY =15;

    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private boolean mSignOutClicked;

    private ConnectionResult mConnectionResult;

    private SignInButton btnSignIn;
    private Button btnSignOut;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;
    private LinearLayout llProfileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);

        // Button click listeners
        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);

        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Button on click listener
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mSignOutClicked=false;
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }else{
            mSignOutClicked = true;
            signOutFromGplus();
        }

    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;

        if (!mSignOutClicked) {
            // Get user's information
            getProfileInformation();

            // Update the UI after signin
            updateUI(true);

            if (mGoogleApiClient.isConnected()) {
                launchAppActivities();
            }
        }


    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
        }
    }
    /**
     * Sign-in into google
     * */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }
    /**
     * Sign-out from google
     * */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.disconnect();
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }

    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void launchAppActivities() {
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
        if (Plus.AccountApi.getAccountName(mGoogleApiClient) != null) {
            System.out.println("launching activity");
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String userId = currentPerson.getId();
            new GetUserInfoAfterSignIn(null, this).execute(userId);
        }
    }

    public void launchMainActivity() {
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
        System.out.println("Launching Main Activity");
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
        String userId = currentPerson.getId();
        String imageUrl = currentPerson.getImage().getUrl();
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra("email", email);
        mainIntent.putExtra("userId", userId);
        mainIntent.putExtra("imageUrl", imageUrl);
        this.startActivityForResult(mainIntent, RC_MAIN_ACTIVITY);
    }

    public void launchInterestsActivity(com.affinitymapper.affinitymapper.model.Person person) {
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
        System.out.println("Launching Interests Activity");
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
        String userId = currentPerson.getId();
        String imageUrl = currentPerson.getImage().getUrl();
        Intent mainIntent = new Intent(this, Interests.class);
        mainIntent.putExtra("email", email);
        mainIntent.putExtra("userId", userId);
        mainIntent.putExtra("imageUrl", imageUrl);
        mainIntent.putExtra("person", person);
        this.startActivityForResult(mainIntent, RC_INT_ACTIVITY);
    }

    public void launchRegistrationActivity() {
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
        System.out.println("Launching Registration activity");
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
        String userId = currentPerson.getId();
        String imageUrl = currentPerson.getImage().getUrl();
        Intent registrationIntent = new Intent(this, RegistrationActivity.class);
        registrationIntent.putExtra("email", email);
        registrationIntent.putExtra("userId", userId);
        registrationIntent.putExtra("imageUrl", imageUrl);
        this.startActivityForResult(registrationIntent, RC_REG_ACTIVITY);
    }

    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                txtName.setText(personName);
                txtEmail.setText(email);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    //Extra Code snippet
    private void getAccessTokenInAsyncTask() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                getAccessToken();
                return null;
            }
        };
        task.execute((Void) null);
    }
    private void getAccessToken() {
        String scopes = "oauth2:server:client_id:506019065654-vsqstco7obec5343l24q2m812mbjogbs.apps.googleusercontent.com:api_scope:" + Scopes.PLUS_LOGIN;
        String accessToken = null;
        try {
            accessToken = GoogleAuthUtil.getToken(
                    this,                                              // Context context
                    Plus.AccountApi.getAccountName(mGoogleApiClient),  // String accountName
                    scopes                                          // String scope
            );
        } catch (IOException transientEx) {
            transientEx.printStackTrace();
            return;
        } catch (UserRecoverableAuthException e) {
            e.printStackTrace();
            startActivityForResult(e.getIntent(), RC_SIGN_IN);
            return;
        } catch (GoogleAuthException authEx) {
            authEx.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}