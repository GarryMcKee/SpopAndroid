package com.example.garrymckee.spop.UserProfile;

import android.util.Log;

import com.example.garrymckee.spop.API.SpotifyAPIService;
import com.example.garrymckee.spop.API.SpotifyApiUtils;
import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.Artist;
import com.example.garrymckee.spop.Model.TopArtists;
import com.example.garrymckee.spop.Model.TopTracks;
import com.example.garrymckee.spop.Model.Track;
import com.example.garrymckee.spop.UI.SpopDisplayPresenter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by garrymckee on 15/03/17.
 */

public class UserTaste {
    private static final String LOG_TAG = UserTaste.class.getSimpleName();

    private List<Track> topTracks;
    private List<Artist> topArtists;
    private Set<String> topGenres;

    private boolean topTracksReady;
    private boolean topArtistsReady;
    private boolean topGenresReady;

    private SpopDisplayPresenter presenter;

    final Observable<TopTracks> topTracksCall;
    final Observable<TopArtists> topArtistsCall;

    public UserTaste(SpopDisplayPresenter presenter) {
        topGenres = new HashSet<>();
        this.presenter = presenter;

        SpotifyAPIService spotifyApiService = SpotifyApiUtils.getSpotifyApiServiceInstance();
        SpopAuthenticator spopAuthenticator = SpopAuthenticator.getInstance();
        String authHeader = "Bearer " + spopAuthenticator.getAuthToken();
        topTracksCall = spotifyApiService.getTopTracks(authHeader);
        topArtistsCall = spotifyApiService.getTopArtists(authHeader);

        initialiseUserTaste();
    }

    private void initialiseUserTaste() {

        topTracksReady = false;
        topArtistsReady = false;
        topGenresReady = false;

        //TODO create better method for chaining/syncing the API calls
        initialiseTopTracks(topTracksCall);

    }

    private Observable initialiseTopTracks(Observable<TopTracks> topTracksCall) {
        topTracksCall
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopTracks>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(LOG_TAG, "Subscribed to top tracks observable");
                    }

                    @Override
                    public void onNext(TopTracks value) {
                        topTracks = value.getTracks();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onUserTasteInitError(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(LOG_TAG, "Processed all data in Top Tracks");
                        topTracksReady = true;
                        initialiseTopArtists(topArtistsCall);
                    }
                });
        return topTracksCall;
    }

    private void initialiseTopArtists(Observable<TopArtists> topArtistsCall) {
        topArtistsCall
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopArtists>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(LOG_TAG, "Subscribed to top artists observable");
                    }

                    @Override
                    public void onNext(TopArtists value) {
                        topArtists = value.getArtists();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onUserTasteInitError(e);
                    }

                    @Override
                    public void onComplete() {
                        topArtistsReady = true;
                        initialiseTopGenres();
                        Log.d(LOG_TAG, "Processed all data in Top Artists");
                    }
                });
    }

    private void initialiseTopGenres(){

        if (!getTopArtists().isEmpty()) {
            for (Artist artist : getTopArtists()) {
                for (String genre : artist.getGenres()){
                    getTopGenres().add(genre);
                }
            }

            topGenresReady = true;
            onUserTasteReady();

        } else {
            onUserTasteInitError("Not enough data to initialise genres");
        }


    }

    private void onUserTasteInitError(String errorMessage){
        Log.e(LOG_TAG, "Could not initialise user taste profile: " + errorMessage);
    }

    private void onUserTasteInitError(Throwable t){
        Log.e(LOG_TAG, "Could not initialise user taste profile: " + t.getMessage());
    }

    private void onUserTasteReady(){
        presenter.onUserProfileReady();
    }

    public String printUserTaste() {
        String userTasteString = "USER TASTE:" + "\n";
        userTasteString += "Top Tracks: " + "\n";

        for (Track track : getTopTracks()) {
            userTasteString += track.getName() + " by " + track.getArtist() + "\n";
        }

        userTasteString += "Top Artists:" + "\n";

        for (Artist artist : getTopArtists()) {
            userTasteString += artist.getName() + "\n";
        }

        userTasteString += "Top Genres:" + "\n";

        for (String genre : getTopGenres()) {
            userTasteString += genre + "\n";
        }

        Log.d(LOG_TAG, userTasteString);

        return userTasteString;
    }

    public List<Track> getTopTracks() {
        return topTracks;
    }

    public List<Artist> getTopArtists() {
        return topArtists;
    }

    public Set<String> getTopGenres() {
        return topGenres;
    }
}
