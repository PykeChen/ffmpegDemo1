package com.example.cpy.ffmpegdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements InitMaterialsTaskAsyncTask.OnMaterialInitListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("MyFFmpeg");
    }

    private TextView mInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfoView = findViewById(R.id.sample_text);

        new InitMaterialsTaskAsyncTask(this).execute();

    }

    public void format(View view) {
        mInfoView.setText(avformatInfo());
    }

    public void codec(View view) {
        mInfoView.setText(avcodecInfo());
    }

    public void filter(View view) {
        mInfoView.setText(avfilterInfo());
    }

    public void config(View view) {
        mInfoView.setText(configurationInfo());
    }

    public native String avformatInfo();
    public native String avcodecInfo();
    public native String avfilterInfo();
    public native String configurationInfo();


    @Override
    public void onMaterialSuccess() {
        new Thread(){

            @Override
            public void run() {
                String materDir = InitMaterialsTaskAsyncTask.getMaterDir(AppApplication.getApplication()).getAbsolutePath();
                JNI.splitPCMFile(materDir + "/44.1k_s16le.pcm", materDir);
            }
        }.start();
    }
}
