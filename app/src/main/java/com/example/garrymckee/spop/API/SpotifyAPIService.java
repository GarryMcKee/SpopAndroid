package com.example.garrymckee.spop.API;

import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.TopArtists;
import com.example.garrymckee.spop.Model.TopTracks;
import com.example.garrymckee.spop.Model.Track;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by garrymckee on 12/03/17.
 */

public interface SpotifyAPIService {
    @GET("tracks/{id}")
    Observable<Track> getTrack(@Path("id") String trackId);

    @GET("tracks/{id}")
    Observable<Track> getRxTrack(@Path("id") String trackId);

    @GET("me/top/artists")
    Observable<TopArtists> getTopArtists(
            @Header("Authorization") String authHeader);

    @GET("me/top/tracks")
    Observable<TopTracks> getTopTracks(
            @Header("Authorization") String authHeader);

    @GET("recommendations")
    Observable<Recommendation> getRecommendations(
            @Header("Authorization") String authHeader,
            @Query("seed_artists") String seedArtists,
            @Query("seed_tracks") String seedTracks,
            @Query("seed_genres") String seedGenres,
            @Query("limit") String limit
    );

}
