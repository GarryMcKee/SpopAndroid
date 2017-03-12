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

    public SpopDisplayPresenter(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SpotifyApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        spotifyAPIService = retrofit.create(SpotifyAPIService.class);
    }

    private SpopDisplayable spopDisplayable;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;
    }

    @Override
    public void requestAuthentication() {
        AuthenticationRequest authRequest = SpopAuthenticator.Authenticate();
        spopDisplayable.launchAuthenticator(authRequest);
    }

    @Override
    public void fetchTrackById(String trackId) {

            Call<Track> trackCall = spotifyAPIService.getTrack("28FJMlLUu9NHuwlZWFKDn7");
            trackCall.enqueue(new Callback<Track>() {
                @Override
                public void onResponse(Call<Track> call, Response<Track> response) {
                    spopDisplayable.displayTrack(response.body());
                }

                @Override
                public void onFailure(Call<Track> call, Throwable t) {
                    spopDisplayable.displayError(t.getLocalizedMessage());
                }
            });
    }
}
