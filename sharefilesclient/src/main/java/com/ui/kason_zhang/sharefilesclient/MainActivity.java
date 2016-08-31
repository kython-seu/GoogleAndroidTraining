package com.ui.kason_zhang.sharefilesclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Intent mRequestFileIntent;
    private ParcelFileDescriptor mInputPFD;
    private static final int REQUESTFILE = 100;
    private TextView text_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: comr in ");
        text_ = (TextView)findViewById(R.id.text);
        mRequestFileIntent = new Intent();
        mRequestFileIntent.setAction(Intent.ACTION_PICK);
        mRequestFileIntent.setType("text/plain");
        startActivityForResult(mRequestFileIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case REQUESTFILE:
                    // Get the file's content URI from the incoming Intent
                    Uri returnUri = returnIntent.getData();
                    /*
                     * Try to open the file for "read" access using the
                     * returned URI. If the file isn't found, write to the
                     * error log and return.
                     */
                    try {
                        /*
                         * Get the content resolver instance for this context, and use it
                         * to get a ParcelFileDescriptor for the file.
                         */
                        mInputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Log.e("MainActivity", "File not found.");
                        return;
                    }
                    // Get a regular file descriptor for the file
                    FileDescriptor fd = mInputPFD.getFileDescriptor();
                    FileInputStream fileInputStream = new FileInputStream(fd);
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    try {
                        while((read = fileInputStream.read(buffer))!= -1) {
                            String text = new String(buffer,0,read);
                            Log.i(TAG, "onActivityResult: the text:"+text);
                            text_.setText(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "onActivityResult: "+fd.toString());
                    break;
                default:
                    break;
            }
        }else{
            Log.i(TAG, "onActivityResult: error");
        }
    }
}
