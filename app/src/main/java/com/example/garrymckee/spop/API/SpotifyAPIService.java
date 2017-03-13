package com.example.garrymckee.spop.API;

import com.example.garrymckee.spop.Model.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.path;

/**
 * Created by garrymckee on 12/03/17.
 */

public interface SpotifyAPIService {
    @GET("tracks/{id}")
    Call<Track> getTrack(@Path("id") String trackId);

    @GET("me/top/{type}")
    Call<List<Track>> getTopTracks(
            @Header("Authorization") String authHeader,
            @Path("type") String type
    );

}
