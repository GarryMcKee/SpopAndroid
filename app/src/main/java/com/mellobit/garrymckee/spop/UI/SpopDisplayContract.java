package com.mellobit.garrymckee.spop.UI;

import com.spotify.sdk.android.authentication.AuthenticationRequest;

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
        void setFavorited();
        void setNotFavorited();
    }

    interface OnNextTrackListener {
        void onNextTrack();
    }
}
