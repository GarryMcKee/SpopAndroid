package com.example.garrymckee.spop.API;

import com.example.garrymckee.spop.Model.TopArtists;
import com.example.garrymckee.spop.Model.TopTracks;
import com.example.garrymckee.spop.Model.Track;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by garrymckee on 12/03/17.
 */

public interface SpotifyAPIService {
    @GET("tracks/{id}")
    Call<Track> getTrack(@Path("id") String trackId);

    @GET("me/top/artists")
    Call<TopArtists> getTopArtists(
            @Header("Authorization") String authHeader
    );

    @GET("me/top/tracks")
    Call<TopTracks> getTopTracks(
            @Header("Authorization") String authHeader
    );

}
