package com.example.garrymckee.spop.UI;

import android.util.Log;

import com.example.garrymckee.spop.API.SpotifyAPIService;
import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.TopArtists;
import com.example.garrymckee.spop.Model.TopTracks;
import com.example.garrymckee.spop.Model.Track;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;

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

    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/";
    private static final String SPOTIFY_AUTH_HEADER = "Authorization: Bearer ";
    private static final String SHORT_TERM_PARAMETER = "short_term";

    private SpotifyAPIService spotifyAPIService;
    private SpopDisplayable spopDisplayable;
    private SpopAuthenticator spopAuthenticator;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        spotifyAPIService = retrofit.create(SpotifyAPIService.class);
        spopAuthenticator = new SpopAuthenticator();
    }

    @Override
    public void requestAuthentication() {
        AuthenticationRequest authRequest = SpopAuthenticator.Authenticate();
        spopDisplayable.launchAuthenticator(authRequest);
    }

    @Override
    public void fetchTopArtists() {
        Call<TopArtists> call = spotifyAPIService.getTopArtists(
                "Bearer " + spopAuthenticator.getAuthToken()
        );

        call.enqueue(new Callback<TopArtists>() {
            @Override
            public void onResponse(Call<TopArtists> call, Response<TopArtists> response) {
                if(response.body() != null){
                    Log.d(LOG_TAG, "RESPONSE:" + "\n" + response.body().toString());
                } else{
                    Log.d(LOG_TAG, "RESPONSE IS EMPTY");
                }
            }

            @Override
            public void onFailure(Call<TopArtists> call, Throwable t) {
                spopDisplayable.displayError(t.getMessage());
            }
        });
    }

    @Override
    public void fetchTopTracks() {
        Call<TopTracks> call = spotifyAPIService.getTopTracks(
                "Bearer " + spopAuthenticator.getAuthToken()
        );

        call.enqueue(new Callback<TopTracks>() {
            @Override
            public void onResponse(Call<TopTracks> call, Response<TopTracks> response) {
                if(response.body() != null){
                    Log.d(LOG_TAG, "RESPONSE:" + "\n" + response.body().toString());
                } else{
                    Log.d(LOG_TAG, "RESPONSE IS EMPTY");
                }

            }

            @Override
            public void onFailure(Call<TopTracks> call, Throwable t) {
                spopDisplayable.displayError(t.getMessage());
            }
        });


    }

    @Override
    public void storeAuthToken(String token) {
        spopAuthenticator.setAuthToken(token);
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
