package com.example.garrymckee.spop.UI;

import android.util.Log;

import com.example.garrymckee.spop.API.SpotifyAPIService;
import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.Track;
import com.example.garrymckee.spop.UserProfile.UserTaste;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.garrymckee.spop.API.SpotifyApiUtils.getSpotifyApiServiceInstance;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopDisplayPresenter implements SpopDisplayPresenterInterface{

    private static final String LOG_TAG = SpopDisplayPresenter.class.getSimpleName();

    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/";
    private static final String SPOTIFY_AUTH_HEADER = "Authorization: Bearer ";
    private static final String SHORT_TERM_PARAMETER = "short_term";

    private SpotifyAPIService spotifyAPIService;
    private SpopDisplayable spopDisplayable;
    private SpopAuthenticator spopAuthenticator;
    private UserTaste userTaste;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;

        spotifyAPIService = getSpotifyApiServiceInstance();
        spopAuthenticator = SpopAuthenticator.getInstance();
    }

    @Override
    public void requestAuthentication() {
        AuthenticationRequest authRequest = SpopAuthenticator.Authenticate();
        spopDisplayable.launchAuthenticator(authRequest);
    }

    @Override
    public void storeAuthToken(String token) {
        spopAuthenticator.setAuthToken(token);
    }

    @Override
    public void generateUserTasteProfile() {
        userTaste = new UserTaste();
    }

    @Override
    public void printUserTasteProfile() {
        if (userTaste.isUserTasteProfileReady()) {
            userTaste.printUserTaste();
        } else {
            Log.d(LOG_TAG, "user taste profile is not ready");
        }
    }
}
