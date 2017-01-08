package com.example.fatemeh.newtest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReflectiveTwoActivity extends AppCompatActivity {
    String message;
    private MediaPlayer mediaPlayer;
    String doc = Integer.toString(BabaYagaActivity.docNum);
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
        setContentView(R.layout.activity_reflective_two);

        //adding background music
        mediaPlayer = MediaPlayer.create(ReflectiveTwoActivity.this, R.raw.reflective2);
        mediaPlayer.start();

    }
    public void AppleBtn(View v) {
        message = "Favorite Character: Apple Tree\n";
        Save(v);
    }
    public void OvenBtn(View v) {
        message = "Favorite Character: Oven\n";
        Save(v);
    }
    public void RiverBtn(View v) {
        message = "Favorite Character: River of Milk\n";
        Save(v);
    }

    // write text to file
    public void Save(View v) {
        // add-write text into file

        String state;
        state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath()+"/SwanGeeseResponses");
            if(!Dir.exists()) {
                Dir.mkdir();
            }
            Dir = new File(Root.getAbsolutePath()+"/SwanGeeseResponses/"+doc);
            if(!Dir.exists()) {
                Dir.mkdir();
            }

            File file = new File(Dir,"ReflectionsQ2.txt");
            //String Message = textmsg.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                //textmsg.setText("");
                //Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "SD card Not Found", Toast.LENGTH_LONG).show();
        }

        startReflectiveThreeActivity(v);
    }
    public void startReflectiveOneActivity(View view) {
        Intent intent = new Intent(this, ReflectiveOneActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void startReflectiveThreeActivity(View view) {
        Intent intent = new Intent(this, ReflectiveThreeActivity.class);
        startActivity(intent);
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
}

