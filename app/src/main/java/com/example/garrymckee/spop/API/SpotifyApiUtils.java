package com.example.garrymckee.spop.API;

import com.example.garrymckee.spop.Model.Track;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by garrymckee on 19/03/17.
 */

public class SpotifyApiUtils {
    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/";

    public static SpotifyAPIService getSpotifyApiServiceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(SpotifyAPIService.class);
    }

    public static List<String> getImageUrlsFromTracks(List<Track> tracks) {
        ArrayList<String> imageUrls = new ArrayList<>();
        for (Track track: tracks) {
            imageUrls.add(track.getAlbum().getImages().get(0).getUrl());
        }

        return imageUrls;
    }
}
