package com.example.fatemeh.newtest;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class AppleActivity extends AppCompatActivity implements View.OnDragListener, View.OnTouchListener {
    private static final String TAG = "junk";
    private MediaPlayer mediaPlayer;
    public static int docNum;
    Date initDate;
    int task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = 0;
        initDate = new Date();
        //Landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //No title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_apple);
        //adding background music
        mediaPlayer = MediaPlayer.create(AppleActivity.this, R.raw.apple);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

//        register a long click listener for the balls
//        findViewById(R.id.apple_yellow).setOnLongClickListener(this);
//        findViewById(R.id.apple_green).setOnLongClickListener(this);
//        findViewById(R.id.apple_red).setOnLongClickListener(this);
        findViewById(R.id.apple_yellow).setOnTouchListener(this);
        findViewById(R.id.apple_green).setOnTouchListener(this);
        findViewById(R.id.apple_red).setOnTouchListener(this);

//        register drag event listeners for the target layout containers
        findViewById(R.id.container).setOnDragListener(this);

        findViewById(R.id.basket_yellow).setOnDragListener(this);
        findViewById(R.id.basket_green).setOnDragListener(this);
        findViewById(R.id.basket_red).setOnDragListener(this);
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

    public void saveTime(long t) {
        String state;
        state = Environment.getExternalStorageState();
        String message = String.valueOf(t);

        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath()+"/SwanGeeseResponses/");
            if(!Dir.exists()) {
                Dir.mkdir();
            }
            for(int i = 0; i < 1000; i++) {
                Dir = new File(Root.getAbsolutePath()+"/SwanGeeseResponses/"+Integer.toString(i));
                if(!Dir.exists()) {
                    Dir.mkdir();
                    docNum = i;
                    break;
                }
            }


            File file = new File(Dir,"AppleTime-seconds.txt");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                //textmsg.setText("");
                //Toast.makeText(getApplicationContext(), "Message Saved", Toast.LENGTH_SHORT).show();
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

    public void startIntroductionActivity(View view) {
        Intent intent = new Intent(this, IntroductionActivity.class);
        startActivity(intent);
    }

    public void startVidAppleOvenActivity(View view) {
        Date finDate = new Date();
        long timediff = finDate.getTime() - initDate.getTime();
        long mintimediff = TimeUnit.MILLISECONDS.toSeconds(timediff);
        saveTime(mintimediff);
        Intent intent = new Intent(this, VidAppleOvenActivity.class);
        startActivity(intent);
    }

    public void check(View v) {
        task++;
        if(task == 3) startVidAppleOvenActivity(v);
    }

    public void closeApp(View view) {
        this.finish();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    //    called when ball has been touched and held
    @Override
    public boolean onTouch(View imageView, MotionEvent me) {
        //        the ball has been touched
//            create clip data holding data of the type MIMETYPE_TEXT_PLAIN
        ClipData clipData = ClipData.newPlainText("", "");

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
            /*start the drag - contains the data to be dragged,
            metadata for this data and callback for drawing shadow*/
        imageView.startDrag(clipData, shadowBuilder, imageView, 0);
//        we're dragging the shadow so make the view invisible
        imageView.setVisibility(View.INVISIBLE);
        return true;
    }

    //    called when the ball starts to be dragged
//    used by top and bottom layout containers
    @Override
    public boolean onDrag(View receivingLayoutView, DragEvent dragEvent) {
        final View draggedImageView = (View) dragEvent.getLocalState();
        ViewGroup draggedImageViewParentLayout = (ViewGroup) draggedImageView.getParent();


        // Handles each of the expected events
        switch (dragEvent.getAction()) {

            case DragEvent.ACTION_DRAG_STARTED:
                Log.i(TAG, "drag action started");

                // Determines if this View can accept the dragged data
                if (dragEvent.getClipDescription()
                        .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    Log.i(TAG, "Can accept this data");

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                } else {
                    Log.i(TAG, "Can not accept this data");

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                Log.i(TAG, "drag action entered");
//                the drag point has entered the bounding box
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                Log.i(TAG, "drag action location");
                /*triggered after ACTION_DRAG_ENTERED
                stops after ACTION_DRAG_EXITED*/
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                Log.i(TAG, "drag action exited");
//                the drag shadow has left the bounding box
                return true;

            case DragEvent.ACTION_DROP:
                  /* the listener receives this action type when
                  drag shadow released over the target view
            the action only sent here if ACTION_DRAG_STARTED returned true
            return true if successfully handled the drop else false*/
                switch (draggedImageView.getId()) {
                    case R.id.apple_yellow:
                        if (receivingLayoutView.getId() == R.id.basket_yellow) {
                            draggedImageViewParentLayout.removeView(draggedImageView);
                            PercentRelativeLayout newLayout = (PercentRelativeLayout) receivingLayoutView.getParent();
                            PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) draggedImageView.getLayoutParams();
                            PercentRelativeLayout.LayoutParams basket_params = (PercentRelativeLayout.LayoutParams) findViewById(R.id.basket_yellow).getLayoutParams();
                            params.getPercentLayoutInfo().topMarginPercent = basket_params.getPercentLayoutInfo().topMarginPercent;
                            params.getPercentLayoutInfo().leftMarginPercent = basket_params.getPercentLayoutInfo().leftMarginPercent + 0.03f;
                            newLayout.addView(draggedImageView);
                            draggedImageView.setVisibility(View.VISIBLE);
                            check(receivingLayoutView);
                            return true;
                        } else return false;
                    case R.id.apple_green:
                        if (receivingLayoutView.getId() == R.id.basket_green) {
                            draggedImageViewParentLayout.removeView(draggedImageView);
                            PercentRelativeLayout newLayout = (PercentRelativeLayout) receivingLayoutView.getParent();
                            PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) draggedImageView.getLayoutParams();
                            PercentRelativeLayout.LayoutParams basket_params = (PercentRelativeLayout.LayoutParams) findViewById(R.id.basket_green).getLayoutParams();
                            params.getPercentLayoutInfo().topMarginPercent = basket_params.getPercentLayoutInfo().topMarginPercent;
                            params.getPercentLayoutInfo().leftMarginPercent = basket_params.getPercentLayoutInfo().leftMarginPercent + 0.03f;
                            newLayout.addView(draggedImageView);
                            draggedImageView.setVisibility(View.VISIBLE);
                            check(receivingLayoutView);
                            return true;
                        } else return false;
                    case R.id.apple_red:
                        if (receivingLayoutView.getId() == R.id.basket_red) {
                            draggedImageViewParentLayout.removeView(draggedImageView);
                            PercentRelativeLayout newLayout = (PercentRelativeLayout) receivingLayoutView.getParent();
                            PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) draggedImageView.getLayoutParams();
                            PercentRelativeLayout.LayoutParams basket_params = (PercentRelativeLayout.LayoutParams) findViewById(R.id.basket_red).getLayoutParams();
                            params.getPercentLayoutInfo().topMarginPercent = basket_params.getPercentLayoutInfo().topMarginPercent;
                            params.getPercentLayoutInfo().leftMarginPercent = basket_params.getPercentLayoutInfo().leftMarginPercent + 0.03f;
                            newLayout.addView(draggedImageView);
                            draggedImageView.setVisibility(View.VISIBLE);
                            check(receivingLayoutView);
                            return true;
                        } else return false;
                    default:
                        Log.i(TAG, "in default");
                        return false;
                }

            case DragEvent.ACTION_DRAG_ENDED:

                Log.i(TAG, "drag action ended");
                Log.i(TAG, "getResult: " + dragEvent.getResult());

//                if the drop was not successful, set the ball to visible
                if (!dragEvent.getResult()) {
                    Log.i(TAG, "setting visible");
//                    draggedImageView.setVisibility(View.VISIBLE);
                    draggedImageView.post(new Runnable() {
                        @Override
                        public void run() {
                            draggedImageView.setVisibility(View.VISIBLE);
                        }
                    });
                }

                return true;
            // An unknown action type was received.
            default:
                Log.i(TAG, "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}
