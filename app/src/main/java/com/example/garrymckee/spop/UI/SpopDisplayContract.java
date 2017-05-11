package com.example.garrymckee.spop.UI;

import android.content.Context;

import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.Track;
import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import java.util.List;

/**
 * Created by garrymckee on 05/03/17.
 */

public interface SpopDisplayContract {

    interface SpopDisplayPresentable {
        void requestAuthentication();
        void storeAuthToken(String token);
        void onRecommendationsReady(List<TrackRecommendation> recommendations);
        void fetchRecommendations();
        List<TrackRecommendation> getRecommendations();
        void getNextRecommendation();
        void initialisePlayer(Context ctx, String authToken);
        void playTrackFromUri(String spotifyUri);
    }

    interface SpopDisplayable {

        void launchAuthenticator(AuthenticationRequest request);
        void displayRecommendations(TrackRecommendation recommendation);
        Context getApplicationContext();

    }

    interface TransportPresenter {
        void getNextRecommendation();
        void playTrackFromUri(String spotifyUri);
    }

    interface TransportDisplayable {

    }
}
