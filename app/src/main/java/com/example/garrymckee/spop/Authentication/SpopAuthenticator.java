package com.example.garrymckee.spop.Authentication;

import android.content.SharedPreferences;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopAuthenticator {

    private static final String CLIENT_ID = "3c111ba9afb74477a09347b0b62da582";
    private static final String REDIRECT_URI = "spop://callback";
    private static final String LOG_TAG = SpopAuthenticator.class.getSimpleName();

    private static SpopAuthenticator instance;

    private String authToken;

    private SpopAuthenticator() {
        Log.d(LOG_TAG, "Created instance of class");
    }

    public static SpopAuthenticator getInstance() {
        if(instance == null) {
            instance = new SpopAuthenticator();
        }
        return instance;
    }

    public static AuthenticationRequest Authenticate(){
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(
                CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming", "user-top-read", "user-library-modify", "user-library-read"});
        AuthenticationRequest request = builder.build();
        return request;
    }

    public void setAuthToken(String authToken) {
        instance.authToken = authToken;
    }

    public String getAuthToken() {
        return this.authToken;
    }
}
