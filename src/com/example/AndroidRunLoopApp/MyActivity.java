package com.example.AndroidRunLoopApp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyActivity extends Activity {

    private static String TAG = "RunLoopApp";

    LooperThread looperThread;

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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                looperThread.mHandler.handleMessage(Message.obtain());
            }
        }, 1000 * 3);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
