package com.mellobit.garrymckee.spop.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mellobit.garrymckee.spop.Playback.CurrentTrack;
import com.mellobit.garrymckee.spop.R;

/**
 * Created by Garry on 11/05/2017.
 */

public class TransportFragment extends Fragment implements SpopDisplayContract.TransportDisplayable{

    private TransportPresenter presenter;

    private SpopDisplayContract.OnNextTrackListener onNextTrackListener;

    private ImageButton favoriteButton;
    private ImageButton nextRecommendationButton;
    private ImageButton playButton;

    public TransportPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TransportPresenter(this, getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        favoriteButton = (ImageButton) view.findViewById(R.id.save_track_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSaveTrack();
            }
        });

        nextRecommendationButton = (ImageButton) view.findViewById(R.id.next_recommendation_button);
        nextRecommendationButton.setOnClickListener(v -> onNextTrackListener.onNextTrack());

        playButton = (ImageButton) view.findViewById(R.id.play_pause_button);
        playButton.setOnClickListener(v -> {
            presenter.playTrackFromUri(CurrentTrack.getInstance().getCurrentTrackUri());
        });


        presenter.UpdateTransportUi();

        return view;
    }

    @Override
    public void setPlaying() {
        playButton.setImageResource(R.drawable.ic_pause_black_48dp);
    }

    @Override
    public void setPaused() {
        playButton.setImageResource(R.drawable.play_icon);
    }

    @Override
    public void setFavorited() {
        favoriteButton.setImageResource(R.drawable.ic_favorite_black_48dp);
    }

    @Override
    public void setNotFavorited() {
        favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_48dp);
    }

    public void setOnNextTrackListener(SpopDisplayContract.OnNextTrackListener onNextTrackListener) {
        this.onNextTrackListener = onNextTrackListener;
    }
}
