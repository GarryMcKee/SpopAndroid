package com.example.garrymckee.spop.UI;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.garrymckee.spop.API.SpotifyAPIService;
import com.example.garrymckee.spop.API.SpotifyApiUtils;
import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.Track;
import com.example.garrymckee.spop.Playback.CurrentTrack;
import com.example.garrymckee.spop.Playback.SpotifyPlayerWrapper;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void UpdateTransportUi() {
        if (playerWrapper.isPlaying()) {
            transportDisplayable.setPlaying();
        } else {
            transportDisplayable.setPaused();
        }

        if(CurrentTrack.getInstance().isCurrentTrackSaved()) {
            transportDisplayable.setFavorited();
        } else {
            transportDisplayable.setNotFavorited();
        }

    }

    @Override
    public void onSaveTrack() {
        String trackUri = CurrentTrack.getInstance().getCurrentTrackUri();
        SpotifyAPIService spotifyApiService = SpotifyApiUtils.getSpotifyApiServiceInstance();

        SpopAuthenticator spopAuthenticator = SpopAuthenticator.getInstance();
        String authHeader = "Bearer " + spopAuthenticator.getAuthToken();

        if(CurrentTrack.getInstance().isCurrentTrackSaved()) {
            unSaveCurrentTrack(trackUri, spotifyApiService, authHeader);
        } else {
            saveCurrentTrack(trackUri, spotifyApiService, authHeader);
        }


    }

    private void saveCurrentTrack(String trackUri, SpotifyAPIService spotifyApiService, String authHeader) {
        spotifyApiService.saveTrack(authHeader, trackUri).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(LOG_TAG, "Success!");
                transportDisplayable.setFavorited();
                CurrentTrack.getInstance().setCurrentTrackSaved(true);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(LOG_TAG, "Failure: " + t.getMessage());
            }
        });
    }

    private void unSaveCurrentTrack(String trackUri, SpotifyAPIService spotifyApiService, String authHeader) {
        spotifyApiService.deleteTrack(authHeader, trackUri).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(LOG_TAG, "Success!");
                transportDisplayable.setNotFavorited();
                CurrentTrack.getInstance().setCurrentTrackSaved(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(LOG_TAG, "Failure: " + t.getMessage());
            }
        });
    }

    @Override
    public void playTrackFromUri(String spotifyUri) {
        playerWrapper.playTrackFromUri(CurrentTrack.getInstance().getCurrentTrackUri());
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
