package com.kason.lrucachedemo;

import android.graphics.Bitmap;
import android.os.HandlerThread;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    HandlerThread handlerThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LruCache<String,Bitmap> bitmapLruCache = new LruCache<>(1000);
    }
}
