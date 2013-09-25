package com.example.AndroidRunLoopApp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyActivity extends Activity {

    private static String TAG = "RunLoopApp";

    LooperThread looperThread;

    Timer timer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        looperThread = new LooperThread();
        looperThread.start();


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                looperThread.mHandler.sendMessage(Message.obtain());
            }
        }, 0, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        looperThread.mHandler.post(new Runnable() {
            @Override
            public void run() {
                Looper.myLooper().quit();
            }
        });
    }

    class LooperThread extends Thread {
        public Handler mHandler;

        @Override
        public void run() {
            Looper.prepare();

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.d(TAG, ">>>>>>handle message: " + msg);
                }
            };

            Looper.loop();

            Log.d(TAG, ">>>>>>thread quit.");
        }
    }
}
