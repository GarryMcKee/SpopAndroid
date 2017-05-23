package com.mellobit.garrymckee.spop.UI;

import com.mellobit.garrymckee.spop.Authentication.SpopAuthenticator;
import com.mellobit.garrymckee.spop.Model.TrackRecommendation;
import com.mellobit.garrymckee.spop.Playback.CurrentTrack;
import com.mellobit.garrymckee.spop.Recommendation.RecommendationHolder;
import com.mellobit.garrymckee.spop.Recommendation.RecommendationManager;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.mellobit.garrymckee.spop.UI.SpopDisplayContract.*;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopDisplayPresenter implements SpopDisplayPresentable {

    private SpopDisplayable spopDisplayable;
    private SpopAuthenticator spopAuthenticator;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;
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
    public void onRecommendationsReady() {
        spopDisplayable.onRecommendationsReady();
    }

    @Override
    public void setCurrentTrackUri(int trackPosition) {
        TrackRecommendation currentTrack = RecommendationHolder
                .getInstance()
                .getRecommendations()
                .get(trackPosition);

        CurrentTrack
                .getInstance()
                .setCurrentTrackRecommendation(currentTrack);
    }

    @Override
    public void onTrackSelected(String trackUri) {

    }
}
