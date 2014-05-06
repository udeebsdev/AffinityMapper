package com.affinitymapper.affinitymapper.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.affinitymapper.affinitymapper.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

import java.io.IOException;

public class SignInActivity extends Activity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 10;
    private static final int RC_MAIN_ACTIVITY = 11;
    private static final int RC_SIGN_OUT = 12;

    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        View signInButton = (SignInButton) this.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API, null).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
                System.out.println("onConnectionFailed");
            }
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mSignInClicked = false;
        //getAccessTokenInAsyncTask();
    }

    @Override
    public void onClick(View view) {
        System.out.println("onClick");
        if (view.getId() == R.id.signInButton
                && !mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (!mGoogleApiClient.isConnecting()) {
                System.out.println("onActivityResult isConnecting");
                mGoogleApiClient.connect();
            }
            if (responseCode != RESULT_OK) {
                System.out.println("onActivityResult RESULT NOT OK");
                mSignInClicked = false;
            }
            mIntentInProgress = false;

            if (mGoogleApiClient.isConnected()) {
                System.out.println("onActivityResult isConnected");
                String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.putExtra("accountName", accountName);
                this.startActivityForResult(mainIntent, RC_MAIN_ACTIVITY);

            } else {
                resolveSignInError();
            }
        } else if (requestCode == RC_SIGN_OUT) {
            System.out.println("onActivityResult request code sign out");
            if (mGoogleApiClient.isConnected()) {
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                        .setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status arg0) {
                                mGoogleApiClient.connect();
                            }
                        });
            }

            System.out.println("onActivityResult disconnect");

            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }

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

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    private void resolveSignInError() {
        System.out.println("resolveSignInError");

        //This is a hack. For some reason mConnectionResult is always null when the app is not freshly installed.
        if (mConnectionResult == null && mGoogleApiClient.isConnected()) {
            System.out.println("resolveSignInError connection result is null");
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra("accountName", Plus.AccountApi.getAccountName(mGoogleApiClient));
            startActivityForResult(mainIntent, RC_MAIN_ACTIVITY);
        } else if (mConnectionResult.hasResolution()) {
            System.out.println("resolveSignInError has resolution");
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                System.out.println("resolveSignInError error carught" + e.getLocalizedMessage());
                e.printStackTrace();
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
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
