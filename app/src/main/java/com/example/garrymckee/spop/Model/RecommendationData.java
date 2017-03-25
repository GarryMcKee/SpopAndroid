package com.example.garrymckee.spop.Model;

/**
 * Created by garrymckee on 25/03/17.
 */

public class RecommendationData {

    private String trackName;
    private String albumName;
    private String artistName;
    private String imageUrl;
    private String id;

    public RecommendationData (String trackName,
            String albumName,
            String artistName,
            String imageUrl,
            String id) {
        this.trackName = trackName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }
}
