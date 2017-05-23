package com.mellobit.garrymckee.spop.Model;

/**
 * Created by garrymckee on 27/03/17.
 */

public class RecommendationInput {
    private String artistInput;
    private String trackInput;
    private String genreInput;

    public RecommendationInput(String artistInput, String trackInput, String genreInput) {
        this.artistInput = artistInput;
        this.trackInput = trackInput;
        this.genreInput = genreInput;
    }

    public String getArtistInput() {
        return artistInput;
    }

    public String getTrackInput() {
        return trackInput;
    }

    public String getGenreInput() {
        return genreInput;
    }
}