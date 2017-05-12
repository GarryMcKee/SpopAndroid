package com.example.garrymckee.spop.UI;

import android.util.Log;

/**
 * Created by Garry on 11/05/2017.
 */

public class TransportPresenter implements SpopDisplayContract.TransportPresenter {

    private static final String LOG_TAG = TransportPresenter.class.getSimpleName();

    @Override
    public void getNextRecommendation() {
        //
    }

    @Override
    public void playTrackFromUri(String spotifyUri) {
        Log.d(LOG_TAG, "playing track");
    }
}
