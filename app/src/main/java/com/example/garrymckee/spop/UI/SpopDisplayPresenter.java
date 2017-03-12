package com.example.garrymckee.spop.UI;

import android.util.Log;

import com.example.garrymckee.spop.API.SpotifyAPIService;
import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.Track;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopDisplayPresenter implements SpopDisplayPresenterInterface{

    private static final String LOG_TAG = SpopDisplayPresenter.class.getSimpleName();
    private static String SpotifyApiBaseUrl = "https://api.spotify.com/v1/";
    private SpotifyAPIService spotifyAPIService;

    private SpopDisplayable spopDisplayable;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SpotifyApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        spotifyAPIService = retrofit.create(SpotifyAPIService.class);
    }

    @Override
    public void requestAuthentication() {
        AuthenticationRequest authRequest = SpopAuthenticator.Authenticate();
        spopDisplayable.launchAuthenticator(authRequest);
    }

    @Override
    public void fetchTrackById(String trackId) {

            final Call<Track> trackCall = spotifyAPIService.getTrack("28FJMlLUu9NHuwlZWFKDn7");
            trackCall.enqueue(new Callback<Track>() {
                @Override
                public void onResponse(Call<Track> call, Response<Track> response) {
                    Log.d(LOG_TAG, call.request().url().toString());
                    if(response.body()!=null){
                        spopDisplayable.displayTrack(response.body());
                    } else {
                        spopDisplayable.displayError("Track was null");
                    }
                }

                @Override
                public void onFailure(Call<Track> call, Throwable t) {
                    spopDisplayable.displayError(t.getLocalizedMessage());
                }
            });
    }
}
