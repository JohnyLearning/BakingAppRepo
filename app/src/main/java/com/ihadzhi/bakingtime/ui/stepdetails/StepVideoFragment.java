package com.ihadzhi.bakingtime.ui.stepdetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.databinding.FragmentRecipeStepVideoBinding;
import com.ihadzhi.bakingtime.model.Step;

public class StepVideoFragment extends Fragment {

    private static final String STEP_PARAM = "step";
    private FragmentRecipeStepVideoBinding dataBinding;

    public StepVideoFragment() {
    }

    public static StepVideoFragment newInstance(Step step) {

        Bundle args = new Bundle();
        args.putParcelable(STEP_PARAM, step);
        StepVideoFragment fragment = new StepVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_video, container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            Step step = getArguments().getParcelable(STEP_PARAM);
            if (step != null) {
                setupPlayer(step.getVideoURL());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void setupPlayer(String url) {
        releasePlayer();
        Context context = getActivity();
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(context).build();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getString(R.string.app_name)));
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url));
        dataBinding.stepVideoPlayer.setPlayer(player);
        player.prepare(videoSource);
        player.seekTo(0);
        player.setPlayWhenReady(true);
    }

    // Release player
    private void releasePlayer() {
        Player player = dataBinding.stepVideoPlayer.getPlayer();
        if (player != null) {
            player.stop();
            player.release();
        }
    }
}
