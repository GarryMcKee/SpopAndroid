package com.example.garrymckee.spop.UI;

import com.example.garrymckee.spop.Model.Recommendation;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

/**
 * Created by garrymckee on 05/03/17.
 */

public interface SpopDisplayContract {

    interface SpopDisplayPresenterInterface {
        void requestAuthentication();
        void storeAuthToken(String token);
        void generateUserTasteProfile();
        void onUserProfileReady();
        void fetchRecommendations();
        void init();
    }

    interface SpopDisplayable {

        void launchAuthenticator(AuthenticationRequest request);
        void displayRecommendations(Recommendation recommendation);

    }
}
