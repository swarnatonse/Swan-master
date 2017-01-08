package com.example.fatemeh.newtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.example.fatemeh.newtest.BabaYagaActivity;

public class ReflectiveThreeActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    String doc = Integer.toString(BabaYagaActivity.docNum);

    private static final String myName = "ReflectiveThreeActivity";
    private MediaRecorder myAudioRecorder;
    private boolean myAudioRecorderRunning;

    //Delay in ms
    private static final int DELAY = 11000;

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
        setContentView(R.layout.activity_reflective_three);
        //get pic
        DrawingView mypic = (DrawingView)findViewById(R.id.see_img);
        //mypic = drawView;
        String pathName = "/sdcard/SwanGeeseResponses/"+doc+"/BabaYaga.jpg";
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        BitmapDrawable bd = new BitmapDrawable(res, bitmap);
        mypic.setBackgroundDrawable(bd);

        //adding background music
        mediaPlayer = MediaPlayer.create(ReflectiveThreeActivity.this, R.raw.reflective3);
        mediaPlayer.start();

        myAudioRecorder = new MediaRecorder();

        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        String time = dateFormat.format(date);
        String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SwanGeeseResponses/"+doc+"/reflectionsQ3_"+time+".3gp";
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
        myAudioRecorderRunning = false;
        ImageView img = (ImageView) findViewById(R.id.button_done);
        img.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                    myAudioRecorderRunning = true;
                    Toast.makeText(getApplicationContext(), "Recording Audio...", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e(myName, "CANNOT RECORD AUDIO");
                }
            }
        }, DELAY);

    }

    public void startReflectiveTwoActivity(View view) {
        Intent intent = new Intent(this, ReflectiveTwoActivity.class);
        startActivity(intent);
    }

    public void startEndActivity(View view) {
        Intent intent = new Intent(this, EndActivity.class);
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

    public void stopRecording(View view) {
        if (myAudioRecorderRunning) {
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;
            Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
        }
    }
}
