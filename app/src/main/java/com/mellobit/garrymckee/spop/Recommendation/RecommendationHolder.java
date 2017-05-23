package com.mellobit.garrymckee.spop.Recommendation;

import android.util.Log;

import com.mellobit.garrymckee.spop.Model.TrackRecommendation;

import java.util.List;

/**
 * Created by Garry on 11/05/2017.
 */

public class RecommendationHolder {

    private static RecommendationHolder instance;

    private List<TrackRecommendation> recommendations;

    public static RecommendationHolder getInstance () {
        if(instance == null) {
            instance = new RecommendationHolder();
            return instance;
        } else {
            return instance;
        }
    }

    public List<TrackRecommendation> getRecommendations() {
        if(recommendations == null) {
            Log.d("CheckNullList", "recommendations null");
        }
        return recommendations;
    }

    public void setRecommendations(List<TrackRecommendation> trackRecommendations) {
        recommendations = trackRecommendations;
        Log.d("CheckList", "Size is: " + recommendations.size());
    }

    private RecommendationHolder () {

    }
}
