package com.example.garrymckee.spop.Model;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by garrymckee on 12/03/17.
 */

public class Track{

    String name;
    String id;
    String uri;
    boolean isPlayable;

    Album album;
    List<Artist> artists;

    public String getName(){
        return this.name;
    }
    public String getId() {
        return this.id;
    }
    public String getArtistName() {
        return artists.get(0).getName();
    }
    public Album getAlbum() {
        return this.album;
    }


}
