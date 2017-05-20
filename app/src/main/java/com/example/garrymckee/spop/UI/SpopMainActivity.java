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

import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.example.garrymckee.spop.R;
import com.example.garrymckee.spop.Recommendation.RecommendationHolder;
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

    private static final String KEY_VIEWPAGER_POSITION = "view_pager_position";


    private SpopDisplayPresentable presenter;
    private ViewPager trackViewPager;
    private int lastViewPagerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            lastViewPagerPosition = savedInstanceState.getInt(KEY_VIEWPAGER_POSITION);
        }
        presenter = new SpopDisplayPresenter(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_spop_main);

        if(SpopAuthenticator.getInstance().getAuthToken() == null) {
            Log.d(LOG_TAG, "Not authenticated");
            presenter.requestAuthentication();
        } else {
            setupUI();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_VIEWPAGER_POSITION, lastViewPagerPosition);
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
    public void onRecommendationsReady() {
        //Once model layer is ready display data
        setupUI();
    }

    private void setupUI() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Set up view pager
        trackViewPager = (ViewPager) findViewById(R.id.trackview_viewpager);

        trackViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                TrackRecommendation recommendation = RecommendationHolder.getInstance().getRecommendations().get(position);
                return TrackViewFragment.newInstance(recommendation);
            }

            @Override
            public int getCount() {
                return RecommendationHolder.getInstance().getRecommendations().size();
            }
        });

        trackViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //TODO This entire function is a bit hacky
                presenter.setCurrentTrackUri(position);

                //This should only really be called on rotation changes, don't replay the song if onPageSelected was called without acctually changing the track
                if(position != lastViewPagerPosition) {
                    ((TransportFragment)getSupportFragmentManager().findFragmentById(R.id.transport_container))
                            .getPresenter()
                            .playTrackFromUri(RecommendationHolder.getInstance().getRecommendations().get(position).getId());
                } else {
                    Log.d(LOG_TAG, "Called on Page Selected without changing selected item");
                }

                lastViewPagerPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        presenter.setCurrentTrackUri(trackViewPager.getCurrentItem());

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