package com.ui.kason_zhang.receivefromotherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.ShareActionProvider;
import android.widget.TextView;

import android.support.v7.widget.ShareActionProvider;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if(intent!=null){
            if(Intent.ACTION_SEND.equals(action)&&"text/plain".equals(type)){
                handleText(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem actionShareItem = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(actionShareItem);
        //mShareActionProvider = (ShareActionProvider)actionShareItem.getActionProvider();
        //MenuItemCompat.setActionProvider(actionShareItem,mShareActionProvider);
        Log.i(TAG, "onCreateOptionsMenu: ----->"+mShareActionProvider);
        return true;//return true to display me+nu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                Log.i(TAG, "onOptionsItemSelected: comr on");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"SEND TEXT VIA SHAREACTIONPROVIDER");
                shareIntent.setType("text/plain");
                setShareIntent(shareIntent);
                break;
        }
        return true;
    }

    private void setShareIntent(Intent shareIntent){
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private void handleText(Intent intent) {
        String result = intent.getStringExtra(Intent.EXTRA_TEXT);
        textView.setText(result);
        Log.i(TAG, "handleText: result:"+result);
    }
}
