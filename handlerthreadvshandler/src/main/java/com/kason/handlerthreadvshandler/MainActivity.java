package com.kason.handlerthreadvshandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Handler mHandler = new MyHandler(MainActivity.this);
    private HandlerThread mHandlerThread = new HandlerThread("HandlerThread");
    int count = 0;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "Handler Thread ID is : "+Thread.currentThread().getId()+" "+count);
            count++;
            //SystemClock.sleep(2000);
            mHandler.postDelayed(runnable,2000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Main Thread : "+Thread.currentThread().getId());
        mHandler.post(runnable);
    }
    private static class MyHandler extends Handler{
        private WeakReference<Activity> mActivity;
        public MyHandler(Activity activity){
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem meu = menu.add(1,100,0,"HandlerThread");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                mHandler.removeCallbacks(runnable);
                mHandlerThread.start();
                mHandler = new Handler(mHandlerThread.getLooper());
                mHandler.post(runnable);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
