package com.example.garrymckee.spop.Model;

import java.util.List;

/**
 * Created by garrymckee on 14/03/17.
 */

public class TopArtists {

    List<Artist> items;

    public List<Artist> getArtists() {
        return items;
    }

    @Override
    public String toString() {
        String result = "";
        for (Artist artist : items){
            result+= artist.getName() + "\n";
        }

        return result;
    }
}
