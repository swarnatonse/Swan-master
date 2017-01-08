package com.example.fatemeh.newtest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import java.util.concurrent.TimeUnit;
//import android.R;

public class EndActivity extends AppCompatActivity {

    public Date initDate = IntroductionActivity.date;
    String doc = Integer.toString(BabaYagaActivity.docNum);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date finDate = new Date();
        long timediff = finDate.getTime() - initDate.getTime();
        long mintimediff = TimeUnit.MILLISECONDS.toMinutes(timediff);
        saveTime(mintimediff);

        super.onCreate(savedInstanceState);
        //Landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //No title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end);



}
    public void saveTime(long t) {
        String state;
        state = Environment.getExternalStorageState();
        String message = String.valueOf(t);

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

            File file = new File(Dir,"EntireGamePlayTime.txt");
            //String Message = textmsg.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                //textmsg.setText("");
                Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "SD card Not Found", Toast.LENGTH_LONG).show();
        }
    }

    public void startStartActivity(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void startReflectiveOneActivity(View view) {
        this.finish();
        Intent intent = new Intent(this, ReflectiveOneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
