package com.sai.interviewtaskapp;

import android.net.Uri;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sai.interviewtaskapp.Model.ApiData;

import java.util.ArrayList;

import static android.media.session.PlaybackState.ACTION_SKIP_TO_NEXT;
import static android.media.session.PlaybackState.ACTION_SKIP_TO_PREVIOUS;

public class VideoDisplayActivity extends AppCompatActivity {

    String title, description, thumb, videourl;
    TextView tv_title, tv_description;
    ApiData selectedItemList = new ApiData();
    ArrayList<ApiData> apiDataArrayList = new ArrayList<>();
    ArrayList<String> videoListUrl = new ArrayList<>();
    PlayerView playerView;
    SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);

        if (getIntent() != null) {

            apiDataArrayList = getIntent().getParcelableArrayListExtra("list");

            if (apiDataArrayList != null && apiDataArrayList.size() > 0) {
                for (int i = 0; i < apiDataArrayList.size(); i++) {
                    videoListUrl.add(apiDataArrayList.get(i).getUrl());
                }
                Log.i("###", "urlVideoList-->" + videoListUrl.toString());
            }

            selectedItemList = getIntent().getExtras().getParcelable("data");
            title = getIntent().getExtras().getString("title");
            description = getIntent().getExtras().getString("description");
            thumb = getIntent().getExtras().getString("thumb");
            videourl = getIntent().getExtras().getString("url");

            playerView = (PlayerView) findViewById(R.id.exerciseVideo);
            tv_title = findViewById(R.id.tv_title);
            tv_description = findViewById(R.id.tv_description);

            if (title != null && description != null) {
                tv_title.setText(title);
                tv_description.setText(description);
            }


        }



    }

    @Override
    protected void onStart() {
        super.onStart();


        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());

        playerView.setPlayer(player);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "ExoPlayer"));

        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).
                createMediaSource(Uri.parse(videourl));

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);


    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;
    }


}

