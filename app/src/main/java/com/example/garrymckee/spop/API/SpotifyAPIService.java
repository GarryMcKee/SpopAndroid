package com.example.garrymckee.spop.API;

import com.example.garrymckee.spop.Model.Genres;
import com.example.garrymckee.spop.Model.Recommendation;
import com.example.garrymckee.spop.Model.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by garrymckee on 12/03/17.
 */

public interface SpotifyAPIService {
    @GET("recommendations")
    Call<List<Recommendation>> getRecommendations(
            @Header("user") String authToken,
            @Query("seed_artists") String seedArtists,
            @Query("seed_tracks") String seedTracks,
            @Query("seed_genres") String seedGenres
    );

    @GET("recommendations/available-genre-seeds")
    Call<Genres> getGenreSeeds(@Header("user") String authToken);

    @GET("tracks")
    Call<Track> getTrack(@Path("id") String trackId);
}
