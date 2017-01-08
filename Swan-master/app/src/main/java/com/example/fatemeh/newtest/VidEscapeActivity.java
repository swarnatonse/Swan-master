package com.example.fatemeh.newtest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VidEscapeActivity extends AppCompatActivity {

    private VideoView mVideoView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //No title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vid_escape);
        /**
         * INTRO VIDEO
         */
        Button buttonPlayVideo2 = (Button) findViewById(R.id.playButton);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        //displays a video file
        mVideoView2 = (VideoView) findViewById(R.id.VidEscapeVideo);

        //Media Controller
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView2);
        mVideoView2.setMediaController(mediaController);

        String uriPath2 = "android.resource://com.example.fatemeh.newtest/" + R.raw.escapefinal;
        Uri uri2 = Uri.parse(uriPath2);
        mVideoView2.setVideoURI(uri2);
        mVideoView2.requestFocus();
        mVideoView2.start();

        buttonPlayVideo2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView mVideoView2 = (VideoView) findViewById(R.id.VidEscapeVideo);
                // VideoView mVideoView = new VideoView(this);
//                String uriPath = "android.resource://com.example.fatemeh.newtest/" + R.raw.intro;
//                Uri uri2 = Uri.parse(uriPath);
//                mVideoView2.setVideoURI(uri2);
//                mVideoView2.requestFocus();
//                mVideoView2.start();
                if (mVideoView2.isPlaying()) {
                    mVideoView2.pause();
                } else {
                    mVideoView2.start();
                }
            }
        });

        mVideoView2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                startEndActivity(mVideoView2);
            }
        });

    }
    public void startBabaYagaActivity(View view) {
        Intent intent = new Intent(this, BabaYagaActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void startEndActivity(View view) {
        Intent intent = new Intent(this, EndActivity.class);
        startActivity(intent);
    }

    public void closeApp(View view) {
        this.finish();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}



