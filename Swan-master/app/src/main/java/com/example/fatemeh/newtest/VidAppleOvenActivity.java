package com.example.fatemeh.newtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VidAppleOvenActivity extends AppCompatActivity {

    private static final String myName = "VidAppleOvenActivity";

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
        setContentView(R.layout.activity_vid_apple_oven);

        /**
         * INTRO VIDEO
         */
        Button buttonPlayVideo2 = (Button) findViewById(R.id.playButton);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        //displays a video file
        mVideoView2 = (VideoView) findViewById(R.id.VidAppleOvenVideo);

        //Media Controller
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView2);
        mVideoView2.setMediaController(mediaController);

        String uriPath2 = "android.resource://com.example.fatemeh.newtest/" + R.raw.appleoven;
        Uri uri2 = Uri.parse(uriPath2);
        mVideoView2.setVideoURI(uri2);
        mVideoView2.requestFocus();
        mVideoView2.start();

        buttonPlayVideo2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView mVideoView2 = (VideoView) findViewById(R.id.VidAppleOvenVideo);
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
                startOvenActivity(mVideoView2);
            }
        });

        SharedPreferences sharedpreferences = getSharedPreferences(Utils.PREF_PREFIX + myName, Context.MODE_PRIVATE);
        int level = sharedpreferences.getInt(Utils.COMPLETED_LEVEL, 0);
        if (level < 3) {
            Log.e(myName, "WRONG COMPLETED_LEVEL VALUE");
        } else if (level == 3) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(Utils.COMPLETED_LEVEL, 4);
            editor.commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView2.isPlaying()) {
            mVideoView2.pause();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!mVideoView2.isPlaying()) {
            mVideoView2.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Cleanup
        mVideoView2.stopPlayback();
    }

    public void startAppleActivity(View view) {
        Intent intent = new Intent(this, AppleActivity.class);
        startActivity(intent);
    }

    public void startOvenActivity(View view) {
        Intent intent = new Intent(this, OvenActivity.class);
        startActivity(intent);
    }

}
