package com.example.garrymckee.spop.UI;

import com.example.garrymckee.spop.Model.Track;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import java.util.List;

/**
 * Created by garrymckee on 05/03/17.
 */

public interface SpopDisplayContract {

    interface SpopDisplayPresenterInterface {
        void requestAuthentication();
        void fetchTrackById(String trackId);
        void fetchTopArtists();
        void fetchTopTracks();
        void storeAuthToken(String token);
    }

    interface SpopDisplayable {

        void launchAuthenticator(AuthenticationRequest request);
        void displayTopTracks(List<Track> tracks);
        void displayTrack(Track track);
        void displayError(String errorMessage);

    }
}
