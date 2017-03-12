package com.example.garrymckee.spop.Model;

import java.util.List;

/**
 * Created by garrymckee on 12/03/17.
 */

public class Track {

    String name;
    String id;
    String uri;
    boolean isPlayable;

    Album album;
    List<Artist> artists;

    public String getName(){
        return this.name;
    }

}
