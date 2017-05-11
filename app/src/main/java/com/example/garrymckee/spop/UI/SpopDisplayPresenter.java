package com.example.garrymckee.spop.UI;

import android.content.Context;
import android.util.Log;

import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.example.garrymckee.spop.Playback.SpotifyPlayerWrapper;
import com.example.garrymckee.spop.Recommendation.RecommendationManager;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.List;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopDisplayPresenter implements SpopDisplayPresentable {

    private SpopDisplayable spopDisplayable;
    private SpopAuthenticator spopAuthenticator;
    private SpotifyPlayerWrapper player;
    private int recommendationIndex;
    private List<TrackRecommendation> recommendations;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;
        this. recommendationIndex = 0;
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
    public void fetchRecommendations() {
        RecommendationManager rm = new RecommendationManager(this);
        rm.generateRecommendations();
    }

    @Override
    public List<TrackRecommendation> getRecommendations() {
        return recommendations;
    }

    @Override
    public void getNextRecommendation() {
        recommendationIndex++;
        spopDisplayable.displayRecommendations(recommendations.get(recommendationIndex));
    }

    @Override
    public void onRecommendationsReady(List<TrackRecommendation> recommendations) {
        this.recommendations = recommendations;
        spopDisplayable.displayRecommendations(this.recommendations.get(recommendationIndex));
    }

    @Override
    public void initialisePlayer(Context ctx, String authToken) {
        //player = new SpotifyPlayerWrapper(ctx, authToken);
    }

    @Override
    public void playTrackFromUri(String spotifyUri) {
        Log.d("PRESENTER", "playing track");
        player.playTrackFromUri(spotifyUri);
    }
}
