package com.mellobit.garrymckee.spop.Model;

import java.util.List;

/**
 * Created by garrymckee on 12/03/17.
 */

public class Recommendation {

    List<Track> tracks;

    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public String toString() {
        String result = "";

        for (Track track : tracks){
            result+= track.getName() + "\n";
        }

        return result;
    }
}
