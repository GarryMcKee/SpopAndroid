package com.example.garrymckee.spop.API;

import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.TopArtists;
import com.example.garrymckee.spop.Model.TopTracks;
import com.example.garrymckee.spop.Model.Track;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by garrymckee on 12/03/17.
 */

public interface SpotifyAPIService {

    @GET("me/top/artists")
    Single<TopArtists> getTopArtists(
            @Header("Authorization") String authHeader);

    @GET("me/top/tracks")
    Single<TopTracks> getTopTracks(
            @Header("Authorization") String authHeader);

    @GET("recommendations")
    Single<Recommendation> getRecommendations(
            @Header("Authorization") String authHeader,
            @Query("seed_artists") String seedArtists,
            @Query("seed_tracks") String seedTracks,
            @Query("seed_genres") String seedGenres,
            @Query("limit") String limit
    );

}
