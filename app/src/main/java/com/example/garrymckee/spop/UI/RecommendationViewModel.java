package com.example.garrymckee.spop.UI;

import com.example.garrymckee.spop.Model.RecommendationData;

import java.util.List;

/**
 * Created by garrymckee on 25/03/17.
 */

public class RecommendationViewModel {

    List<RecommendationData> recommendationData;

    public RecommendationViewModel(List<RecommendationData> recommendationData) {
        this.recommendationData = recommendationData;
    }

    public void bind() {

    }
}
