package com.example.garrymckee.spop.UI;

import android.util.Log;

import com.example.garrymckee.spop.API.SpotifyAPIService;
import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.Artist;
import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.Track;
import com.example.garrymckee.spop.UserProfile.UserTaste;
import com.spotify.sdk.android.authentication.AuthenticationRequest;

import com.example.garrymckee.spop.UI.SpopDisplayContract.*;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.garrymckee.spop.API.SpotifyApiUtils.getSpotifyApiServiceInstance;

/**
 * Created by garrymckee on 05/03/17.
 */

public class SpopDisplayPresenter implements SpopDisplayPresenterInterface{

    private static final String LOG_TAG = SpopDisplayPresenter.class.getSimpleName();

    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/";
    private static final String SHORT_TERM_PARAMETER = "short_term";
    private static final String RECCOMENDATIONS_POOL_LIMIT = "100";

    private SpotifyAPIService spotifyAPIService;
    private SpopDisplayable spopDisplayable;
    private SpopAuthenticator spopAuthenticator;
    private UserTaste userTaste;

    public SpopDisplayPresenter(SpopDisplayable spopDisplayable){
        this.spopDisplayable = spopDisplayable;

        spotifyAPIService = getSpotifyApiServiceInstance();
        spopAuthenticator = SpopAuthenticator.getInstance();

    }

    @Override
    public void init() {
        generateUserTasteProfile();
    }

    @Override
    public void requestAuthentication() {
        AuthenticationRequest authRequest = SpopAuthenticator.Authenticate();
        spopDisplayable.launchAuthenticator(authRequest);
    }

    @Override
    public void storeAuthToken(String token) {
        spopAuthenticator.setAuthToken(token);
    }

    @Override
    public void generateUserTasteProfile() {
        userTaste = new UserTaste(this);
    }

    @Override
    public void onUserProfileReady() {
        userTaste.printUserTaste();
        fetchRecommendations();
    }

    @Override
    public void fetchRecommendations() {
        List<Artist> topArtists = userTaste.getTopArtists();
        List<Track> topTracks = userTaste.getTopTracks();
        String[] topGenres = userTaste.getTopGenres().
                toArray(new String[userTaste.getTopGenres().size()]);

        String authHeader = "Bearer " + spopAuthenticator.getAuthToken();

        String recommendationArtists =
                topArtists.get(0).getId() + ','
                + topArtists.get(1).getId() + ','
                + topArtists.get(2).getId();

        String recommendationTrack = topTracks.get(0).getId();
        String recommendationGenre = topGenres[0];

        spotifyAPIService
                .getRecommendations(
                        authHeader,
                        recommendationArtists,
                        recommendationTrack,
                        recommendationGenre,
                        RECCOMENDATIONS_POOL_LIMIT)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommendation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(LOG_TAG, "Subscribed to recommendations observable");
                    }

                    @Override
                    public void onNext(Recommendation value) {
                        spopDisplayable.displayRecommendations(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(LOG_TAG, "processed recomendations");
                    }
                });
    }
}
