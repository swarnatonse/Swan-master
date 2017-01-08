package com.example.fatemeh.newtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends AppCompatActivity {

    /**
     * Activity workflow:
     * 1. Start
     * 2. Introduction
     * 3. Apple
     * 4. VidAppleOven
     * 5. Oven
     * 6. OvenUhvat
     * 7. VidOvenRiver
     * 8. River
     * 9. VidBabaYaga
     * 10. BabaYaga
     * 11. VidEscape
     * 12. VidFinal
     */
    private MediaPlayer mediaPlayer;

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

        setContentView(R.layout.activity_start);
        //adding background music
        mediaPlayer = MediaPlayer.create(StartActivity.this, R.raw.beginning);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        SharedPreferences sharedpreferences = getSharedPreferences(Utils.PREF_PREFIX + "StartActivity", Context.MODE_PRIVATE);
        //Never set, initialize it
        int level = sharedpreferences.getInt(Utils.COMPLETED_LEVEL, 0);
        if (level == 0) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(Utils.COMPLETED_LEVEL, 1);
            editor.commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //CLEANUP
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void startIntroductionActivity(View view) {
        Intent intent = new Intent(this, IntroductionActivity.class);
        startActivity(intent);
        this.finish();
    }

}
