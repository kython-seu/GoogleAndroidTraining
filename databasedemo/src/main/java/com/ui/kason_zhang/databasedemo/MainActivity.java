package com.ui.kason_zhang.databasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.MyDatabaseUtil;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.create)
    Button create;
    @Bind(R.id.add)
    Button add;
    @Bind(R.id.delete)
    Button delete;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.query)
    Button query;

    private MyDatabaseUtil myDatabaseUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myDatabaseUtil = new MyDatabaseUtil(this);
    }

    @OnClick({R.id.create, R.id.add, R.id.delete, R.id.update, R.id.query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create:
                myDatabaseUtil.create();
                break;
            case R.id.add:
                myDatabaseUtil.insert();
                break;
            case R.id.delete:
                myDatabaseUtil.delete();
                break;
            case R.id.update:
                myDatabaseUtil.update();
                break;
            case R.id.query:
                myDatabaseUtil.query();
                break;
        }
    }
}
