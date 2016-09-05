package com.ui.kason_zhang.customcontentproviderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import database.MyDatabaseUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDatabaseUtil myDatabaseUtil = new MyDatabaseUtil(this);
        myDatabaseUtil.create();
        for(int i=0;i<5;i++) {
            myDatabaseUtil.insert();
        }
    }
}
