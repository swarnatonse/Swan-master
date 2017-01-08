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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntroductionActivity extends AppCompatActivity {

    private static final String myName = "IntroductionActivity";
    public static Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        date = new Date();

        super.onCreate(savedInstanceState);
        //Landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //No title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_introduction);
        /**
         * INTRO VIDEO
         */
        Button buttonPlayVideo2 = (Button) findViewById(R.id.playButton);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        //displays a video file
        final VideoView mVideoView2 = (VideoView) findViewById(R.id.IntroductionVideo);

        //Media Controller
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView2);
        mVideoView2.setMediaController(mediaController);

        String uriPath2 = "android.resource://com.example.fatemeh.newtest/" + R.raw.intro;
        Uri uri2 = Uri.parse(uriPath2);
        mVideoView2.setVideoURI(uri2);
        mVideoView2.requestFocus();
        mVideoView2.start();

        buttonPlayVideo2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoView mVideoView2 = (VideoView) findViewById(R.id.IntroductionVideo);
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
                startAppleActivity(mVideoView2);
            }
        });

        SharedPreferences sharedpreferences = getSharedPreferences(Utils.PREF_PREFIX + myName, Context.MODE_PRIVATE);
        int level = sharedpreferences.getInt(Utils.COMPLETED_LEVEL, 0);
        if (level == 0) {
            Log.e(myName, "WRONG COMPLETED_LEVEL VALUE");
        } else if (level == 1) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(Utils.COMPLETED_LEVEL, 2);
            editor.commit();
        }
    }


    public void startStartActivity(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void startAppleActivity(View view) {
        Intent intent = new Intent(this, AppleActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void closeApp(View view) {
        this.finish();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
