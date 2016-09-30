package com.kason.asynctaskdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AsyncTask<String,Integer,String> mAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Main: Thread ID： "+Thread.currentThread().getId());

        new MyAsynctask("MyAsynctask#1",MainActivity.this).execute("");
        /*new MyAsynctask("MyAsynctask#2",MainActivity.this).execute("");
        new MyAsynctask("MyAsynctask#3",MainActivity.this).execute("");
        new MyAsynctask("MyAsynctask#4",MainActivity.this).execute("");
        new MyAsynctask("MyAsynctask#5",MainActivity.this).execute("");*/
    }
    private class MyAsynctask extends AsyncTask<String,Integer,String>{
        private static final String TAG = "MyAsynctask";
        private String name = "myAsynTask";
        private int count = 0;
        private WeakReference<Activity> mActivityWeakReference;
        public MyAsynctask(String name,Activity activity){
            super();
            this.name = name;
            mActivityWeakReference = new WeakReference<Activity>(activity);
        }
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "doInBackground: Thread ID: "+Thread.currentThread().getId());
            SystemClock.sleep(3000);//模拟耗时任务
            //模拟更新进度条
            publishProgress(count++);
            if(isCancelled()){//一旦执行了取消任务，让task赶紧结束，让它不再继续做未完成的任务了。
                return name;
            }
            return name;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "onPostExecute: Thread ID: "+Thread.currentThread().getId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Log.i(TAG, s+ " execute finish at "+simpleDateFormat.format(new Date()));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute: Thread ID: "+Thread.currentThread().getId());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, "onProgressUpdate: Thread ID: "+Thread.currentThread().getId());
        }
    }
}
