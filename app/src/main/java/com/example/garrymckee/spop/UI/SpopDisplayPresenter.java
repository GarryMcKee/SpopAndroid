package com.example.garrymckee.spop.UI;

import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.example.garrymckee.spop.Recommendation.RecommendationManager;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;

import java.util.List;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopDisplayPresenter implements SpopDisplayPresenterInterface{

    private SpopDisplayable spopDisplayable;
    private SpopAuthenticator spopAuthenticator;
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
    public void getNextRecommendation() {
        recommendationIndex++;
        spopDisplayable.displayRecommendations(recommendations.get(recommendationIndex));
    }

    @Override
    public void onRecommendationsReady(List<TrackRecommendation> recommendations) {
        this.recommendations = recommendations;
        spopDisplayable.displayRecommendations(this.recommendations.get(recommendationIndex));
    }

}
