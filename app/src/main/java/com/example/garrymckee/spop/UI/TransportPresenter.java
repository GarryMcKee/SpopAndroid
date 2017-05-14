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
    private SpopDisplayContract.TransportDisplayable transportDisplayable;

    public TransportPresenter(SpopDisplayContract.TransportDisplayable transportDisplayable, Context ctx) {
        this.transportDisplayable = transportDisplayable;
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

    @Override
    public void pauseCurrentTrack() {
        playerWrapper.pauseTrack();
    }

    @Override
    public void onPlay() {
        transportDisplayable.setPlaying();
    }

    @Override
    public void onPause() {
        transportDisplayable.setPaused();
    }
}
