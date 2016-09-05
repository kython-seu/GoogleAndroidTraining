package com.ui.kason_zhang.customcontentproviderdemoclient;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver contentResolver = MainActivity.this.getContentResolver();
        Uri uri = Uri.parse("content://com.ui.kason_zhang.customcontentproviderdemo.provider/book");
        ContentValues values = new ContentValues();
        values.put("author","lilei");
        values.put("price",99.99);
        values.put("pages",100);
        values.put("name","Python");
        Uri insert = contentResolver.insert(uri, values);
        String newid = insert.getPathSegments().get(0);
        Log.i(TAG, "onCreate: insert successfully");
    }
}
