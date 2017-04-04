package com.example.garrymckee.spop.Model;

/**
 * Created by garrymckee on 25/03/17.
 */

public class TrackRecommendation {

    private String trackName;
    private String albumName;
    private String artistName;
    private String imageUrl;
    private String id;

    public TrackRecommendation(Track track, String imageUrl) {
        this.trackName = track.getName();
        this.albumName = track.getAlbum().getName();
        this.artistName = track.getArtistName();
        this.id = track.getId();
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        return this.trackName;
    }
}
