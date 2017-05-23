package com.mellobit.garrymckee.spop.Recommendation;

import android.util.Log;

import com.mellobit.garrymckee.spop.API.SpotifyAPIService;
import com.mellobit.garrymckee.spop.API.SpotifyApiUtils;
import com.mellobit.garrymckee.spop.Authentication.SpopAuthenticator;
import com.mellobit.garrymckee.spop.Model.Artist;
import com.mellobit.garrymckee.spop.Model.Recommendation;
import com.mellobit.garrymckee.spop.Model.RecommendationInput;
import com.mellobit.garrymckee.spop.Model.TopArtists;
import com.mellobit.garrymckee.spop.Model.TopTracks;
import com.mellobit.garrymckee.spop.Model.Track;
import com.mellobit.garrymckee.spop.Model.TrackRecommendation;
import com.mellobit.garrymckee.spop.UI.SpopDisplayContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by garrymckee on 15/03/17.
 */

public class RecommendationManager {
    private static final String RECOMMENDATIONS_POOL_LIMIT = "50";

    private final SpopDisplayContract.SpopDisplayPresentable presenter;

    private SpotifyAPIService spotifyApiService;
    private SpopAuthenticator spopAuthenticator;
    private String authHeader;

    public RecommendationManager(SpopDisplayContract.SpopDisplayPresentable presenter) {
        this.presenter = presenter;
        spotifyApiService = SpotifyApiUtils.getSpotifyApiServiceInstance();
        spopAuthenticator = SpopAuthenticator.getInstance();
        authHeader = "Bearer " + spopAuthenticator.getAuthToken();
    }

    public void generateRecommendations() {
        Single<TopTracks> topTracksCall;
        Single<TopArtists> topArtistsCall;

        topTracksCall = spotifyApiService.getTopTracks(authHeader);
        topArtistsCall = spotifyApiService.getTopArtists(authHeader);

        Single.zip(topTracksCall, topArtistsCall,
            (topTracks, topArtists) -> createRecommendationInput(
                    topArtists.getArtists(), topTracks.getTracks()))
                .flatMap(new Function<RecommendationInput, Single<Recommendation>>() {
                    @Override
                    public Single<Recommendation> apply(RecommendationInput recommendationInput) throws Exception {
                        return spotifyApiService.getRecommendations(
                                authHeader,
                                recommendationInput.getArtistInput(),
                                recommendationInput.getTrackInput(),
                                recommendationInput.getGenreInput(),
                                RECOMMENDATIONS_POOL_LIMIT);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendation -> {
                    List<Track> tracks = recommendation.getTracks();
                    List<TrackRecommendation> trackRecommendations = new ArrayList<>();
                    for (Track track : tracks) {
                        int index = tracks.indexOf(track);
                        String imageUrl = track.getAlbum().getImages().get(0).getUrl();
                        TrackRecommendation trackRecommendation = new TrackRecommendation(track, imageUrl);
                        trackRecommendations.add(index, trackRecommendation);
                    }

                    trackRecommendations = checkSavedTracks(trackRecommendations);
                    RecommendationHolder.getInstance().setRecommendations(trackRecommendations);
                    presenter.onRecommendationsReady();
                });
    }

    private RecommendationInput createRecommendationInput(List<Artist> artists, List<Track> tracks) {
        ArrayList<Integer> randomArtistIndices = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(artists.size());

            while(randomArtistIndices.contains(index)) {
                index = random.nextInt(artists.size());
            }
            randomArtistIndices.add(index);
        }

        //Get a list of top 3 artists (need ID for api call)
        StringBuilder sb = new StringBuilder("");
        sb.append(artists.get(randomArtistIndices.get(0)).getId() + ',');
        sb.append(artists.get(randomArtistIndices.get(1)).getId() + ',');
        sb.append(artists.get(randomArtistIndices.get(2)).getId());
        String artistInput = sb.toString();

        //Get the users top track (need ID for api call)
        String trackInput = tracks.get(random.nextInt(tracks.size())).getId();

        //Get a list of top genres
        Set<String> genreSet = getGenresFromArtists(artists);
        String[] genres = genreSet.toArray(new String[genreSet.size()]);
        String genreInput = genres[random.nextInt(genres.length)];

        return new RecommendationInput(artistInput, trackInput, genreInput);
    }

    private Set<String> getGenresFromArtists(List<Artist> artists) {
        Set<String> genres = new HashSet<>();
        if (!artists.isEmpty()) {
            for (Artist artist : artists) {
                for (String genre : artist.getGenres()) {
                    genres.add(genre);
                }
            }
            return genres;
        } else {
            return null;
        }
    }

    private List<TrackRecommendation> checkSavedTracks(List<TrackRecommendation> trackRecommendations) {
        String queryIdList = "";
        for(TrackRecommendation track : trackRecommendations) {
            queryIdList += track.getId() + ",";
        }

        Single<boolean[]> trackSavedListCall = spotifyApiService.getSavedTracks(authHeader, queryIdList);
        trackSavedListCall
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trackSavedList -> {
                    for (int i = 0; i < trackSavedList.length; i++) {
                        trackRecommendations.get(i).setSaved(trackSavedList[i]);
                    }
                });

        return trackRecommendations;
    }
}