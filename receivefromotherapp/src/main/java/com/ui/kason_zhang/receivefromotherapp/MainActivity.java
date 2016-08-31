package com.ui.kason_zhang.receivefromotherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.ShareActionProvider;
import android.widget.TextView;

import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: come in--------------");
        textView = (TextView)findViewById(R.id.text);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if(intent!=null){
            Log.i(TAG, "onCreate: -----yes");
            if(Intent.ACTION_SEND.equals(action)){
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
        Log.i(TAG, "handleText: come to the hangleText");
        final Uri returnUri = intent.getData();
        Bundle extras = intent.getExtras();
        String str = intent.getStringExtra(Intent.EXTRA_TEXT);
        textView.setText(str);
        //extras.
        if(returnUri != null) {
            Toast.makeText(MainActivity.this,"it is not null,hehehe",Toast.LENGTH_LONG).show();
            Log.i(TAG, "handleText: returnUri is not null");
            try {
                ParcelFileDescriptor r = getContentResolver().openFileDescriptor(returnUri, "r");
                FileDescriptor fd = r.getFileDescriptor();
                FileInputStream fileInputStream = new FileInputStream(fd);
                byte[] buffer = new byte[1024];
                int read = 0;
                try {
                    while((read = fileInputStream.read(buffer))!= -1) {
                        String text = new String(buffer,0,read);
                        Log.i(TAG, "onActivityResult: the text:"+text);
                        textView.setText(text);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(MainActivity.this,"it is null",Toast.LENGTH_LONG).show();
        }
        //String result = intent.getStringExtra(Intent.EXTRA_TEXT);
        //textView.setText(result);
        //Log.i(TAG, "handleText: result:"+result);
    }
}
