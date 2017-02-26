package com.example.garrymckee.spop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SpopMainActivity extends AppCompatActivity {

    private String spotifyAuthUrl;
    private static final String CLIENT_ID = "3c111ba9afb74477a09347b0b62da582";
    private static final String RESPONSE_TYPE = "token";
    private static final String REDIRECT_URL = "spop://callback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spotifyAuthUrl = "https://accounts.spotify.com/authorize";
        spotifyAuthUrl += "?";
        spotifyAuthUrl += "client_id=";
        spotifyAuthUrl += CLIENT_ID;
        spotifyAuthUrl += "&";
        spotifyAuthUrl += "response_type=";
        spotifyAuthUrl += RESPONSE_TYPE;
        spotifyAuthUrl += "&";
        spotifyAuthUrl += "redirect_uri=";
        spotifyAuthUrl += REDIRECT_URL;

        Log.d("CHECKURL", spotifyAuthUrl);

        setContentView(R.layout.activity_spop_main);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(spotifyAuthUrl));
        startActivity(i);

    }
}
