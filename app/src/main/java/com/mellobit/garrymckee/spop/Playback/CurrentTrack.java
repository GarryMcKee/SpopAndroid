package com.mellobit.garrymckee.spop.Playback;

import com.mellobit.garrymckee.spop.Model.TrackRecommendation;
import com.mellobit.garrymckee.spop.Recommendation.RecommendationHolder;

/**
 * Created by Garry on 12/05/2017.
 */

public class CurrentTrack {

    /*
    Represents the currently selected track (i.e. the track the user is currently looking at)
     */

    private TrackRecommendation currentTrackRecommendation;
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

    public void setCurrentTrackRecommendation(TrackRecommendation currentTrackRecommendation) {
        this.currentTrackRecommendation = currentTrackRecommendation;
    }

    public String getCurrentTrackUri() {
        return currentTrackRecommendation.getId();
    }

    public boolean isCurrentTrackSaved() {
        return currentTrackRecommendation.isSaved();
    }

    public void setCurrentTrackSaved(boolean isSaved) {
        int index = RecommendationHolder.getInstance().getRecommendations().indexOf(currentTrackRecommendation);
        RecommendationHolder.getInstance().getRecommendations().get(index).setSaved(isSaved);
    }
}
