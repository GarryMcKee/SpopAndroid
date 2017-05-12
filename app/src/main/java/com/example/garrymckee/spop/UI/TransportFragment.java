package com.example.garrymckee.spop.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.garrymckee.spop.R;

/**
 * Created by Garry on 11/05/2017.
 */

public class TransportFragment extends Fragment implements SpopDisplayContract.TransportDisplayable{

    TransportPresenter presenter;
    String currentTrackUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TransportPresenter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        ImageButton nextRecommendationButton = (ImageButton) view.findViewById(R.id.next_recommendation_button);
        nextRecommendationButton.setOnClickListener(v -> presenter.getNextRecommendation());

        ImageButton playButton = (ImageButton) view.findViewById(R.id.play_pause_button);
        playButton.setOnClickListener(v -> presenter.playTrackFromUri(currentTrackUri));

        return view;
    }
}
