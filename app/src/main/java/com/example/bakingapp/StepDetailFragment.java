package com.example.bakingapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.bakingapp.data.Recipes;
import com.example.bakingapp.data.Step;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment{
    private PlaybackStateListener playbackStateListener;
    private SimpleExoPlayer player;
    private PlayerView playerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String STEP_ARG = "Step";
    private static final String STEP_POS_ARG = "StepPosition";
    private static final String RECIPE_POS_ARG = "RecipePosition";
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public boolean isPlayWhenReady() {
        return playWhenReady;
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.playWhenReady = playWhenReady;
    }

    public int getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    public long getPlaybackPosition() {
        return playbackPosition;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    // TODO: Rename and change types of parameters
    private Step step;
    private static int stepPosition;
    private static int recipePosition;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param step Step whose details are needed.
     * @param stepPosition Current step position
     * @param recipePosition Current recipe position
     *
     * @return A new instance of fragment StepDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepDetailFragment newInstance(Step step, int stepPosition, int recipePosition) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(STEP_ARG, step);
        args.putInt(STEP_POS_ARG, stepPosition);
        Log.i("StepPositionBundleIn",stepPosition+"");
        args.putInt(RECIPE_POS_ARG, recipePosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(STEP_ARG);
            stepPosition = getArguments().getInt(STEP_POS_ARG);
            Log.i("StepPositionBundleOut",stepPosition+"");
            recipePosition =getArguments().getInt(RECIPE_POS_ARG);
            Log.i("RecipePositionBundleOut",recipePosition+"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        playerView = (PlayerView) rootView.findViewById(R.id.player_view);
        TextView descriptionTextView = (TextView) rootView.findViewById(R.id.description_view);
        FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.player_holder);
        String videoURL = step.getVideoURL();
        playbackStateListener = new PlaybackStateListener();
        if(videoURL==null)
            Log.i("Video","Hello");
        else if(videoURL.equals("")) {
            Log.i("Video", "Hello1");
            playerView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.GONE);
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            //getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        else
            Log.i("Video",videoURL);

        boolean landscape = StepDetailActivity.isLandscape();
        if(landscape){
            descriptionTextView.setVisibility(View.GONE);
            Log.i("Landscape",landscape+"");
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)frameLayout.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            frameLayout.setLayoutParams(params);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
            initializePlayer();

        }
        else {
            initializePlayer();
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(step.getDescription());
        }

        return rootView;
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), "Baking App");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private void initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(getActivity());
        playerView.setPlayer(player);
        Uri mediaUri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(mediaUri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        if (player == null) {
            player.prepare(mediaSource, false, false);
            player.addListener(playbackStateListener);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.removeListener(playbackStateListener);
            player.release();
            player = null;
        }

    }

    private class PlaybackStateListener implements Player.EventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady,
                                         int playbackState) {
            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d("Player state changed", "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }


    /*private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            Context context = getActivity();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            //Initialize the player
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            //Initialize simpleExoPlayerView
            mPlayerView.setPlayer(mExoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "Baking App"));
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
            // Prepare the player with the source.

            mExoPlayer.prepare(videoSource);
            mPlayerView.requestFocus();
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playWhenReady = mExoPlayer.getPlayWhenReady();
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }*/
    /*@Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            Log.d("In Fragment", "onPlayerStateChanged: PLAYING");
        } else if((playbackState == ExoPlayer.STATE_READY)){
            Log.d("In Fragment", "onPlayerStateChanged: PAUSED");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer!=null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        // show user that something went wrong. I am showing dialog but you can use your way
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle("Could not able to stream video");
        adb.setMessage("It seems that something is going wrong.\nPlease try again.");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //finish(); // take out user from this activity. you can skip this
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mExoPlayer.release();   //it is important to release a player
    }*/
}