package com.example.garrymckee.spop.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.example.garrymckee.spop.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

public class SpopMainActivity extends AppCompatActivity
        implements SpotifyPlayer.NotificationCallback,ConnectionStateCallback, SpopDisplayable{

    private static final String LOG_TAG = SpopMainActivity.class.getSimpleName();


    private SpopDisplayPresentable presenter;
    private ViewPager trackViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SpopDisplayPresenter(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_spop_main);

        presenter.requestAuthentication();
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


            //Initialise player and fetch recommendations
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Set up view pager
        trackViewPager = (ViewPager) findViewById(R.id.trackview_viewpager);
        trackViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                TrackRecommendation recommendation = presenter.getRecommendations().get(position);
                return TrackViewFragment.newInstance(recommendation);
            }

            @Override
            public int getCount() {
                return presenter.getRecommendations().size();
            }
        });

        //Set up transport fragment
        fragmentManager
                .beginTransaction()
                .add(R.id.transport_container, new TransportFragment())
                .commit();

    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
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