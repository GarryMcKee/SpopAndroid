package com.example.garrymckee.spop.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.example.garrymckee.spop.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

public class SpopMainActivity extends AppCompatActivity
        implements SpotifyPlayer.NotificationCallback,ConnectionStateCallback, SpopDisplayable{

    private static final String LOG_TAG = SpopMainActivity.class.getSimpleName();
    private static final String CLIENT_ID = "3c111ba9afb74477a09347b0b62da582";

    private SpopDisplayPresenterInterface presenter;

    private TextView trackNameLabel;
    private TextView artistNameLabel;
    private SimpleDraweeView coverArtImage;
    private String currentTrackUri;

    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_spop_main);
        presenter = new SpopDisplayPresenter(this);
        presenter.requestAuthentication();

        trackNameLabel = (TextView) findViewById(R.id.track_name_label);
        artistNameLabel = (TextView) findViewById(R.id.artist_name_label);
        coverArtImage = (SimpleDraweeView) findViewById(R.id.cover_art_image);

        ImageButton nextRecommendationButton = (ImageButton) findViewById(R.id.next_recommendation_button);
        nextRecommendationButton.setOnClickListener(v -> presenter.getNextRecommendation());

        ImageButton playButton = (ImageButton) findViewById(R.id.play_pause_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Current track URI: " + currentTrackUri);

                try{
                    String spotifyURI = "spotify:track:" + currentTrackUri;
                    player.playUri(null, spotifyURI, 0, 0);
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);


            //Instantiate the player
            Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
            Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                @Override
                public void onInitialized(SpotifyPlayer spotifyPlayer) {
                    player = spotifyPlayer;
                    player.addConnectionStateCallback(SpopMainActivity.this);
                    player.addNotificationCallback(SpopMainActivity.this);
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e(LOG_TAG, "Could not initialise player");
                }
            });

            //Fetch recommendations
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                presenter.storeAuthToken(response.getAccessToken());
                presenter.fetchRecommendations();
            }
        }
    }

    @Override
    public void launchAuthenticator(AuthenticationRequest request) {
        Log.d(LOG_TAG, "Launching Authenticator");
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    public void displayRecommendations(TrackRecommendation recommendation) {
        Log.d(LOG_TAG, recommendation.toString());
        coverArtImage.setImageURI(recommendation.getImageUrl());
        trackNameLabel.setText(recommendation.getTrackName());
        artistNameLabel.setText(recommendation.getArtistName());
        currentTrackUri = recommendation.getId();
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

    }
}