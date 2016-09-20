package com.kason.loadermanagerdemo;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.MyDatabaseUtil;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivity";
    @Bind(R.id.list_item)
    ListView listView;
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
    private SimpleCursorAdapter mSimpleCursorAdapter;
    private MyDatabaseUtil myDatabaseUtil;
    //private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myDatabaseUtil = new MyDatabaseUtil(this);
        listView = (ListView)findViewById(R.id.list_item);
        /*mSimpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                null, new String[]{"name", "age"},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);*/
        mSimpleCursorAdapter = new SimpleCursorAdapter(this,R.layout.listview_layout,null,
                new String[]{"name","age"},new int[]{R.id.text1,R.id.text2},0);
        listView.setAdapter(mSimpleCursorAdapter);
        getLoaderManager().initLoader(0, null, MainActivity.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this){
            @Override
            public Cursor loadInBackground() {
                //return super.loadInBackground();
                return myDatabaseUtil.queryAll();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "onLoadFinished start");
        mSimpleCursorAdapter.swapCursor(data);
        listView.setAdapter(mSimpleCursorAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mSimpleCursorAdapter.swapCursor(null);
    }

    @OnClick({R.id.create, R.id.add, R.id.delete, R.id.update, R.id.query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create:
                myDatabaseUtil.create();
                break;
            case R.id.add:
                myDatabaseUtil.insert();
                getLoaderManager().restartLoader(0,null,this);
                break;
            case R.id.delete:
                myDatabaseUtil.delete();
                getLoaderManager().restartLoader(0,null,this);
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
