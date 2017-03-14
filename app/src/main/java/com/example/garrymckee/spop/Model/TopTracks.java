package com.example.garrymckee.spop.Model;

import java.util.List;

/**
 * Created by garrymckee on 14/03/17.
 */

public class TopTracks {

    List<Track> items;

    @Override
    public String toString() {
        String result = "";
        for (Track track : items){
            result+= track.getName() + "\n";
        }

        return result;
    }
}
