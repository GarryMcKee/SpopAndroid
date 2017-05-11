package com.example.garrymckee.spop.Recommendation;

import com.example.garrymckee.spop.API.SpotifyAPIService;
import com.example.garrymckee.spop.API.SpotifyApiUtils;
import com.example.garrymckee.spop.Authentication.SpopAuthenticator;
import com.example.garrymckee.spop.Model.Artist;
import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.RecommendationInput;
import com.example.garrymckee.spop.Model.TopArtists;
import com.example.garrymckee.spop.Model.TopTracks;
import com.example.garrymckee.spop.Model.Track;
import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.example.garrymckee.spop.UI.SpopDisplayContract;
import com.example.garrymckee.spop.UI.SpopDisplayPresenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by garrymckee on 15/03/17.
 */

public class RecommendationManager {
    private static final String RECOMMENDATIONS_POOL_LIMIT = "100";

    private final SpopDisplayContract.SpopDisplayPresentable presenter;

    public RecommendationManager(SpopDisplayContract.SpopDisplayPresentable presenter) {
        this.presenter = presenter;
    }

    public void generateRecommendations() {
        Single<TopTracks> topTracksCall;
        Single<TopArtists> topArtistsCall;

        SpotifyAPIService spotifyApiService = SpotifyApiUtils.getSpotifyApiServiceInstance();
        SpopAuthenticator spopAuthenticator = SpopAuthenticator.getInstance();
        String authHeader = "Bearer " + spopAuthenticator.getAuthToken();

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
                    RecommendationHolder.getInstance().setRecommendations(trackRecommendations);
                    presenter.onRecommendationsReady();
                });
    }

    private RecommendationInput createRecommendationInput(List<Artist> artists, List<Track> tracks) {
        //Get a list of top 3 artists (need ID for api call)
        StringBuilder sb = new StringBuilder("");
        sb.append(artists.get(0).getId() + ',');
        sb.append(artists.get(1).getId() + ',');
        sb.append(artists.get(2).getId());
        String artistInput = sb.toString();

        //Get the users top track (need ID for api call)
        String trackInput = tracks.get(0).getId();

        //Get a list of top genres
        Set<String> genreSet = getGenresFromArtists(artists);
        String[] genres = genreSet.toArray(new String[genreSet.size()]);
        String genreInput = genres[0];

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
}
