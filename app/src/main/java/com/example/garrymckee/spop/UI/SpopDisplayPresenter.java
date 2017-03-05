package com.example.garrymckee.spop.UI;

import android.app.Activity;
import android.content.Context;

import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import static com.example.garrymckee.spop.Authentication.SpopAuthenticator.Authenticate;
import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopDisplayPresenter implements SpopDisplayPresenterInterface{

    private SpopDisplayable spopDisplayable;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;
    }

    @Override
    public void requestAuthentication() {
        AuthenticationRequest authRequest = SpopAuthenticator.Authenticate();
        spopDisplayable.launchAuthenticator(authRequest);
    }
}
