package com.example.garrymckee.spop.UI;

import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.Track;
import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import java.util.List;

/**
 * Created by garrymckee on 05/03/17.
 */

public interface SpopDisplayContract {

    interface SpopDisplayPresenterInterface {
        void requestAuthentication();
        void storeAuthToken(String token);
        void onRecommendationsReady(List<TrackRecommendation> recommendations);
        void fetchRecommendations();
        void getNextRecommendation();
    }

    interface SpopDisplayable {

        void launchAuthenticator(AuthenticationRequest request);
        void displayRecommendations(TrackRecommendation recommendation);

    }
}
