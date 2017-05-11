package com.example.garrymckee.spop.Playback;

import android.content.Context;
import android.util.Log;

import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

/**
 * Created by Garry on 17/04/2017.
 */

public class SpotifyPlayerWrapper implements com.spotify.sdk.android.player.SpotifyPlayer.NotificationCallback,ConnectionStateCallback {

    private static final String LOG_TAG = SpotifyPlayerWrapper.class.getSimpleName();
    private static final String CLIENT_ID = "3c111ba9afb74477a09347b0b62da582";

    private static SpotifyPlayer player;

    public SpotifyPlayerWrapper(Context ctx) {

        //Instantiate the player
        Config playerConfig = new Config(ctx, SpopAuthenticator.getInstance().getAuthToken(), CLIENT_ID);
        Spotify.getPlayer(playerConfig, this, new com.spotify.sdk.android.player.SpotifyPlayer.InitializationObserver() {
            @Override
            public void onInitialized(com.spotify.sdk.android.player.SpotifyPlayer spotifyPlayer) {
                player = spotifyPlayer;
                player.addConnectionStateCallback(SpotifyPlayerWrapper.this);
                player.addNotificationCallback(SpotifyPlayerWrapper.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(LOG_TAG, "Could not initialise player");
            }
        });
    }

    public void playTrackFromUri(String spotifyUri) {
        if (player!=null) {
            player.playUri(null, spotifyUri, 0 , 0);
        } else {
            Log.e(LOG_TAG, "player has not been initialised");
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
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d(LOG_TAG, "PLAYBACK ERROR: " + error.toString());
    }
}
