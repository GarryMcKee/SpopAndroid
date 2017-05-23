package com.mellobit.garrymckee.spop.SavedTracks;

import com.mellobit.garrymckee.spop.Model.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garry on 22/05/2017.
 */

public class SavedTracksHolder {

    private static SavedTracksHolder instance;

    private List<Track> savedTracks;

    private SavedTracksHolder() {
        savedTracks = new ArrayList<>();
    }

    public SavedTracksHolder getInstance() {
        if (instance == null) {
            instance = new SavedTracksHolder();
            return instance;
        } else {
            return instance;
        }
    }

    public void setSavedTracks(List<Track> savedTracks) {
        this.savedTracks = savedTracks;
    }
}
