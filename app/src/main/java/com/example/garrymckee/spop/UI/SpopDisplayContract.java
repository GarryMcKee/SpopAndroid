package com.example.garrymckee.spop.UI;

import com.spotify.sdk.android.authentication.AuthenticationRequest;

/**
 * Created by garrymckee on 05/03/17.
 */

public interface SpopDisplayContract {

    interface SpopDisplayPresenterInterface {
        void requestAuthentication();
    }

    interface SpopDisplayable {

        void launchAuthenticator(AuthenticationRequest request);

    }
}
