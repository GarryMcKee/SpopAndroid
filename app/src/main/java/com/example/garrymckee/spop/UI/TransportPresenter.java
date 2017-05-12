package com.example.garrymckee.spop.UI;

import android.content.Context;
import android.util.Log;

import com.example.garrymckee.spop.Playback.CurrentTrack;
import com.example.garrymckee.spop.Playback.SpotifyPlayerWrapper;

/**
 * Created by Garry on 11/05/2017.
 */

public class TransportPresenter implements SpopDisplayContract.TransportPresenter {

    private static final String LOG_TAG = TransportPresenter.class.getSimpleName();
    private SpotifyPlayerWrapper playerWrapper;

    public TransportPresenter(Context ctx) {
        playerWrapper = new SpotifyPlayerWrapper(ctx);
    }

    @Override
    public void getNextRecommendation() {
        //
    }

    @Override
    public void playTrackFromUri(String spotifyUri) {
        playerWrapper.playTrackFromUri(CurrentTrack.getInstance().getCurrentTrackUri());
    }
}
