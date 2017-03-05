package com.example.garrymckee.spop.Authentication;

import android.app.Activity;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopAuthenticator {

    private static final String CLIENT_ID = "3c111ba9afb74477a09347b0b62da582";
    private static final String REDIRECT_URI = "spop://callback";

    public static AuthenticationRequest Authenticate(){
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(
                CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        return request;
    }
}
