package land.majazi.latifiarchitecure.views.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.daasuu.camerarecorder.CameraRecorder;
import com.daasuu.camerarecorder.CameraRecorderBuilder;
import com.daasuu.camerarecorder.LensFacing;

import java.io.File;

import land.majazi.latifiarchitecure.R;

public class RecordVideo extends AppCompatActivity {

    VideoView video_preview;
    SampleGLView sampleGLView;
    CameraRecorder cameraRecorder;
    LensFacing lensFacing;
    ImageView imageViewRecord;
    ImageView imageViewSwitch;
    ImageView imageViewOk;
    ImageView imageViewRetry;
    FrameLayout frameLayout;
    TextView textViewTime;
    TextView textViewText;
    LinearLayout linearLayoutFinish;
    Handler handlerTime;
    Runnable runnableTime;
    String filePath;
    boolean recording;
    int seconds = 10;
    int time;
    String text;

    //______________________________________________________________________________________________ onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_video);
        video_preview = (VideoView) findViewById(R.id.vv_playback);
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ onCreate
    private void init() {
        filePath = getIntent().getStringExtra("INTENT_NAME_VIDEO_PATH");
        text = getIntent().getStringExtra("INTENT_NAME_VIDEO_TEXT");
        recording = false;
        imageViewRecord = findViewById(R.id.imageViewRecord);
        imageViewSwitch = findViewById(R.id.imageViewSwitch);
        imageViewOk = findViewById(R.id.imageViewOk);
        linearLayoutFinish = findViewById(R.id.linearLayoutFinish);
        frameLayout = findViewById(R.id.wrap_view);
        imageViewRetry = findViewById(R.id.imageViewRetry);
        textViewTime = findViewById(R.id.textViewTime);
        textViewText = findViewById(R.id.textViewText);
        imageViewRecord.setImageDrawable(getResources().getDrawable(R.drawable.dw_back_video_record));
        imageViewRecord.setOnClickListener(v -> recordClick());
        imageViewSwitch.setOnClickListener(v -> switchCamera());
        imageViewOk.setOnClickListener(v -> endRecording());
        imageViewRetry.setOnClickListener(v -> retryRecording());
        textViewText.setText(text);
        linearLayoutFinish.setVisibility(View.INVISIBLE);
        imageViewRecord.setVisibility(View.VISIBLE);
        imageViewSwitch.setVisibility(View.VISIBLE);
        textViewTime.setVisibility(View.INVISIBLE);
        video_preview.setVisibility(View.GONE);
        setFrontCamera();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ initCamera
    private void initCamera() {
        runOnUiThread(() -> {
            sampleGLView = new SampleGLView(getApplicationContext());
            if (cameraRecorder != null) {
                cameraRecorder.stop();
                cameraRecorder = null;
            }
            cameraRecorder = new CameraRecorderBuilder(this, sampleGLView)
                    .lensFacing(lensFacing)
                    .videoSize(720, 1280)
                    .build();
            cameraRecorder.changeAutoFocus();
            frameLayout.addView(sampleGLView);
            imageViewRecord.setVisibility(View.VISIBLE);
        });
    }
    //______________________________________________________________________________________________ initCamera


    //______________________________________________________________________________________________ switchCamera
    private void switchCamera() {
        if (lensFacing == LensFacing.FRONT) {
            setRearCamera();
        } else {
            setFrontCamera();
        }
    }
    //______________________________________________________________________________________________ switchCamera


    //______________________________________________________________________________________________ setFrontCamera
    private void setFrontCamera() {
        refreshRecord();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            lensFacing = LensFacing.FRONT;
            initCamera();
        }, 500);
    }
    //______________________________________________________________________________________________ setFrontCamera


    //______________________________________________________________________________________________ setRearCamera
    private void setRearCamera() {
        refreshRecord();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            lensFacing = LensFacing.BACK;
            initCamera();
        }, 500);
    }
    //______________________________________________________________________________________________ setRearCamera


    //______________________________________________________________________________________________ onCreate
    private void recordClick() {

        if (recording)
            stopRecord();
        else
            startRecord();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ startRecord
    private void startRecord() {
        recording = true;
        cameraRecorder.start(filePath);
        imageViewRecord.setImageDrawable(getResources().getDrawable(R.drawable.dw_back_video_record_stop));
        imageViewSwitch.setVisibility(View.INVISIBLE);
        linearLayoutFinish.setVisibility(View.INVISIBLE);
        startTimeElapse();
    }
    //______________________________________________________________________________________________ startRecord


    //______________________________________________________________________________________________ stopRecord
    private void stopRecord() {
        recording = false;
        cameraRecorder.stop();
        imageViewRecord.setImageDrawable(getResources().getDrawable(R.drawable.dw_back_video_record));
        imageViewSwitch.setVisibility(View.INVISIBLE);
        imageViewRecord.setVisibility(View.INVISIBLE);
        linearLayoutFinish.setVisibility(View.VISIBLE);
        showPreview();
        textViewTime.setVisibility(View.INVISIBLE);
        if (handlerTime != null)
            if (runnableTime != null)
                handlerTime.removeCallbacks(runnableTime);
        handlerTime = null;
        runnableTime = null;
    }
    //______________________________________________________________________________________________ stopRecord



    //______________________________________________________________________________________________ showPreview
    private void showPreview() {

        frameLayout.setVisibility(View.GONE);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            video_preview.setVideoPath(filePath);
            video_preview.setKeepScreenOn(true);
            video_preview.setMediaController(new MediaController(this));
            video_preview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                }
            });
            video_preview.setVisibility(View.VISIBLE);
            video_preview.start();
        }, 500);

    }
    //______________________________________________________________________________________________ showPreview


    //______________________________________________________________________________________________ refreshRecord
    private void refreshRecord() {
        runOnUiThread(() -> {
            try {
                imageViewRecord.setVisibility(View.INVISIBLE);
                sampleGLView.onPause();
                cameraRecorder.stop();
                cameraRecorder.release();
                cameraRecorder = null;
                frameLayout.removeView(sampleGLView);
                sampleGLView = null;
                if (handlerTime != null)
                    if (runnableTime != null)
                        handlerTime.removeCallbacks(runnableTime);

                handlerTime = null;
                runnableTime = null;

            } catch (Exception e) {

            }
        });

    }
    //______________________________________________________________________________________________ refreshRecord


    //______________________________________________________________________________________________ refreshRecord
    private void endRecording() {
        Intent data = new Intent();
        data.putExtra("streetkey", "streetname");
        setResult(RESULT_OK, data);
        finish();
    }
    //______________________________________________________________________________________________ refreshRecord


    //______________________________________________________________________________________________ retryRecording
    private void retryRecording() {
        video_preview.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        linearLayoutFinish.setVisibility(View.INVISIBLE);
        imageViewSwitch.setVisibility(View.VISIBLE);
        imageViewRecord.setVisibility(View.VISIBLE);
        textViewTime.setVisibility(View.INVISIBLE);
        File file = new File(filePath);
        if (file.exists())
            file.delete();
    }
    //______________________________________________________________________________________________ retryRecording


    //______________________________________________________________________________________________ startTimeElapse
    private void startTimeElapse() {

        textViewTime.setVisibility(View.VISIBLE);
        if (handlerTime != null)
            if (runnableTime != null)
                handlerTime.removeCallbacks(runnableTime);
        handlerTime = null;
        runnableTime = null;
        time = seconds;
        handlerTime = new Handler();
        runnableTime = new Runnable() {
            @Override
            public void run() {
                if (time == 0)
                    stopRecord();
                else {
                    textViewTime.setText("00:" + String.format("%02d", seconds) + "  -  00:" + String.format("%02d", time));
                    time--;
                    if (handlerTime != null && runnableTime != null)
                        handlerTime.postDelayed(this::run, 1000);
                }
            }
        };

        handlerTime.postDelayed(runnableTime, 1000);
    }
    //______________________________________________________________________________________________ startTimeElapse


    @Override
    protected void onStart() {
        super.onStart();
        init();
    }


    @Override
    protected void onPause() {
        super.onPause();
        refreshRecord();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
