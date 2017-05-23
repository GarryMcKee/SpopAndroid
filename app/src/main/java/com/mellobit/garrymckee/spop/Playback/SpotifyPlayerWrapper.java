package com.mellobit.garrymckee.spop.Playback;

import android.content.Context;
import android.util.Log;

import com.mellobit.garrymckee.spop.Authentication.SpopAuthenticator;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * Created by Garry on 17/04/2017.
 */

public class SpotifyPlayerWrapper implements ConnectionStateCallback, Player.OperationCallback {


    private static final String LOG_TAG = SpotifyPlayerWrapper.class.getSimpleName();
    private static final String CLIENT_ID = "3c111ba9afb74477a09347b0b62da582";
    private static final String SPOTIFY_URI_PREFIX = "spotify:track:";

    private SpotifyPlayer player;
    private Player.NotificationCallback playerEventListener;

    public SpotifyPlayerWrapper(Context ctx, Player.NotificationCallback playerEventListener) {
        this.playerEventListener = playerEventListener;
        //Instantiate the player
        Config playerConfig = new Config(ctx, SpopAuthenticator.getInstance().getAuthToken(), CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new com.spotify.sdk.android.player.SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(com.spotify.sdk.android.player.SpotifyPlayer spotifyPlayer) {
                player = spotifyPlayer;
                player.addConnectionStateCallback(SpotifyPlayerWrapper.this);
                player.addNotificationCallback(playerEventListener);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(LOG_TAG, "Could not initialise player");
            }
        });
    }

    public void playTrackFromUri(String spotifyUri) {
        if (player != null) {

            if(player.getPlaybackState().positionMs > 0 && player.getMetadata().currentTrack.uri.equals(SPOTIFY_URI_PREFIX + spotifyUri)){
                if (player.getPlaybackState().isPlaying) {
                    player.pause(this);
                } else {
                    player.resume(this);
                }
            } else {
                player.playUri(this, SPOTIFY_URI_PREFIX + spotifyUri, 0 , 0);
            }

        } else {
            Log.e(LOG_TAG, "player has not been initialised");
        }
    }

    public void pauseTrack() {
        player.pause(this);
    }

    public String getCurrentlyPlayingTrack() {
        return player.getMetadata().currentTrack.uri;
    }

    public boolean isPlaying() {
        if(player == null) {
            Log.d(LOG_TAG, "Player is not playing and has not been initialised");
            return false;
        }
        if(player.getPlaybackState().isPlaying) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onLoggedIn() {
        Log.d(LOG_TAG, "USER LOGGED IN");
    }

    @Override
    public void onLoggedOut() {
        Log.d(LOG_TAG, "USER LOGGED OUT");
    }

    @Override
    public void onLoginFailed(Error error) {
        Log.e(LOG_TAG, "LOGIN FAILED: " + error.toString());
    }

    @Override
    public void onTemporaryError() {
        Log.e(LOG_TAG, "TEMPORARY ERROR");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d(LOG_TAG, "CONNECTION MESSAGE: " + s);
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onError(Error error) {

    }

    public void unSubscribePlayerEventListener() {
        if(playerEventListener != null) {
            player.removeNotificationCallback(playerEventListener);
        }
    }
}
