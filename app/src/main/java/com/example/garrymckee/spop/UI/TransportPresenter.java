package com.example.garrymckee.spop.UI;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.garrymckee.spop.Playback.CurrentTrack;
import com.example.garrymckee.spop.Playback.SpotifyPlayerWrapper;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;

/**
 * Created by Garry on 11/05/2017.
 */

public class TransportPresenter implements SpopDisplayContract.TransportPresenter, Player.NotificationCallback {

    private static final String LOG_TAG = TransportPresenter.class.getSimpleName();
    private SpotifyPlayerWrapper playerWrapper;
    private SpopDisplayContract.TransportDisplayable transportDisplayable;

    public TransportPresenter(SpopDisplayContract.TransportDisplayable transportDisplayable, Context ctx) {
        this.transportDisplayable = transportDisplayable;
        playerWrapper = new SpotifyPlayerWrapper(ctx, this);
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

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        if (playerEvent.equals(PlayerEvent.kSpPlaybackNotifyPlay)) {
            transportDisplayable.setPlaying();
        } else if (playerEvent.equals(PlayerEvent.kSpPlaybackNotifyPause)) {
            transportDisplayable.setPaused();
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.e(LOG_TAG, error.toString());
    }
}
