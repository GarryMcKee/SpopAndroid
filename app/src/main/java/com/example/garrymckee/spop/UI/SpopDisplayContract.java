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
        void onRecommendationsReady();
        void fetchRecommendations();
        void setCurrentTrackUri(int trackPosition);
        void onTrackSelected(String trackUri);
    }

    interface SpopDisplayable {
        void launchAuthenticator(AuthenticationRequest request);
        void onRecommendationsReady();
    }

    interface TransportPresenter {
        void playTrackFromUri(String spotifyUri);
        void onSaveTrack();
    }

    interface TransportDisplayable {
        void setPlaying();
        void setPaused();
    }

    interface OnNextTrackListener {
        void onNextTrack();
    }
}
