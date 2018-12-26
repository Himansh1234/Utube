package com.example.youtubex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;

    String API_KEY="AIzaSyC1zuY8lLZ3xDjGvrbN7SNcWEJxJEE1YiI";
    String showVideo;

    private static final int RECOVERY_REQUEST = 1;

    String TAG="VideoActivity";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtubeview);

        youTubePlayerView.initialize(API_KEY, this);

        TextView videoName = findViewById(R.id.view_video_name);
        TextView videodescp = findViewById(R.id.view_video_discription);
        TextView views = findViewById(R.id.view_video_viewcount);

        Bundle bundle = getIntent().getExtras();

         showVideo = bundle.getString("videoId");

         videoName.setText(bundle.getString("videoName"));
        videodescp.setText(bundle.getString("videodec"));
        views.setText("Views : "+bundle.getString("videoView"));


    }



    @Override

    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {





        Log.e(TAG,"Video" +showVideo);

        youTubePlayer.cueVideo(showVideo);



    }



    @Override

    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()) {

            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();

        } else {



            Toast.makeText(this, "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();

        }

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_REQUEST) {

            getYouTubePlayerProvider().initialize(API_KEY, this);

        }

    }



    protected YouTubePlayer.Provider getYouTubePlayerProvider() {

        return youTubePlayerView;

    }



}