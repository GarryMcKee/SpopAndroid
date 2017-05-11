package com.example.garrymckee.spop.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.garrymckee.spop.Model.TrackRecommendation;
import com.example.garrymckee.spop.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Garry on 11/05/2017.
 */

public class TrackViewFragment extends Fragment {

    private static final String ARG_TRACK_NAME = "trackName";
    private static final String ARG_ARTIST_NAME = "artistName";
    private static final String ARG_COVER_ART_URI = "coverArtUri";

    private TextView trackNameLabel;
    private TextView artistNameLabel;
    private SimpleDraweeView coverArtImage;

    private String trackName;
    private String artistName;
    private String coverArtUri;

    public static TrackViewFragment newInstance(TrackRecommendation recommendation) {
        Bundle args = new Bundle();
        args.putString(ARG_TRACK_NAME, recommendation.getTrackName());
        args.putString(ARG_ARTIST_NAME, recommendation.getArtistName());
        args.putString(ARG_COVER_ART_URI, recommendation.getImageUrl());

        TrackViewFragment fragment = new TrackViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trackName = getArguments().getString(ARG_TRACK_NAME);
        artistName = getArguments().getString(ARG_ARTIST_NAME);
        coverArtUri = getArguments().getString(ARG_COVER_ART_URI);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trackview, container, false);

        trackNameLabel = (TextView) v.findViewById(R.id.track_name_label);
        artistNameLabel = (TextView) v.findViewById(R.id.artist_name_label);
        coverArtImage = (SimpleDraweeView) v.findViewById(R.id.cover_art_image);

        trackNameLabel.setText(trackName);
        artistNameLabel.setText(artistName);
        coverArtImage.setImageURI(coverArtUri);

        return v;
    }
}
