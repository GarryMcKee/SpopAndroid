package com.example.garrymckee.spop.Playback;

/**
 * Created by Garry on 12/05/2017.
 */

public class CurrentTrack {

    /*
    Represents the currently selected track (i.e. the track the user is currently looking at)
     */

    private String currentTrackUri;
    private static CurrentTrack instance;

    public static CurrentTrack getInstance() {
       if (instance == null) {
           instance = new CurrentTrack();
           return instance;
       } else {
           return instance;
       }
    }

    private CurrentTrack () {
        //Private constructor
    }

    public void setCurrentTrackUri(String trackUri) {
        this.currentTrackUri = trackUri;
    }

    public String getCurrentTrackUri() {
        return this.currentTrackUri;
    }
}
