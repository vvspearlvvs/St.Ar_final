package com.dragon4.owo.ar_trace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dragon4.owo.ar_trace.ARCore.Activity.GoogleSignActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by YejinChoi on 2017-05-21.
 */


public class InitActivity extends AppCompatActivity {

    private TimerTask mTask;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        mTask = new TimerTask(){
            public void run(){
                Intent intent = new Intent(getApplicationContext(),GoogleSignActivity.class);
                startActivity(intent);
            }
        };

        mTimer= new Timer();

        mTimer.schedule(mTask,3000);

    }

    protected void onDestroy() {
        Log.i("test","onDestroy()");
        mTimer.cancel();
        super.onDestroy();
    }


}
