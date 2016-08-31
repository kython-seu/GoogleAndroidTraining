package com.ui.kason_zhang.sharefiles;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerViewAdapter;

public class FileSelectActivity extends AppCompatActivity {

    private static final String TAG = "FileSelectActivity";
    // The path to the root of this app's internal storage
    private File mPrivateRootDir;
    // The path to the "images" subdirectory
    private File mImagesDir;
    private File mNormalFileDir;
    // Array of files in the images subdirectory
    File[] mImageFiles;
    File[] mNormalfiles;
    // Array of filenames corresponding to mImageFiles
    String[] mImageFilenames;
    Intent mResultIntent;
    private RecyclerView fileRecyclerView;
    private List<String> fileNameList;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);
        initView();
        // Set up an Intent to send back to apps that request a file
        mResultIntent =
                new Intent("com.ui.kason_zhang.sharefiles.ACTION_RETURN_FILE");
        initData();

        // Set the Activity's result to null to begin with
        setResult(Activity.RESULT_CANCELED, null);

        recyclerViewAdapter = new RecyclerViewAdapter(fileNameList,this);
        fileRecyclerView.setAdapter(recyclerViewAdapter);
        fileRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        fileRecyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter.setmRecyclerViewOnClickListener(new RecyclerViewAdapter.
                RecyclerViewOnClickListener() {
            @Override
            public void onClickListener(View view, int postion) {
                /*
                 * Get a File for the selected file name.
                 * Assume that the file names are in the
                 * mList array.
                 */
                File requestFile = new File(mNormalFileDir,fileNameList.get(postion));
                /*
                 * Most file-related method calls need to be in
                 * try-catch blocks.
                 */
                // Use the FileProvider to get a content URI
                Uri uriForFile = null;
                try {
                    uriForFile = FileProvider.getUriForFile(FileSelectActivity.this,
                            "com.ui.kason_zhang.sharefiles.fileprovider",
                            requestFile);
                    //uriForFile.
                } catch (IllegalArgumentException e) {
                    Log.e("File Selector",
                            "The selected file can't be shared: " +
                                    fileNameList.get(postion));
                }
                if(uriForFile != null){
                    // Grant temporary read permission to the content URI
                    mResultIntent.addFlags(
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    // Put the Uri and MIME type in the result Intent
                    mResultIntent.setDataAndType(
                            uriForFile,
                            getContentResolver().getType(uriForFile));
                    Log.i(TAG, "onClickListener: "+getContentResolver().getType(uriForFile));
                    // Set the result
                    FileSelectActivity.this.setResult(Activity.RESULT_OK,
                            mResultIntent);

                } else {
                    mResultIntent.setDataAndType(null, "");
                    FileSelectActivity.this.setResult(RESULT_CANCELED,
                            mResultIntent);
                }
                FileSelectActivity.this.finish();
            }

            @Override
            public void onLongClickListener(View view, int postion) {

            }
        });
    }

    private void initData() {
        fileNameList = new ArrayList<>();//init the ArrayList;

        // Get the files/ subdirectory of internal storage
        mPrivateRootDir = getFilesDir();
        Log.i(TAG, "onCreate: the roor directory of the internal storage:"+mPrivateRootDir);
        mNormalFileDir = new File(mPrivateRootDir,"files");
        // Get the files/images subdirectory;
        mImagesDir = new File(mPrivateRootDir, "images");
        //get all the filename in the directory /data/data/<package>/files/files
        mNormalfiles = mNormalFileDir.listFiles();
        // Get the files in the images subdirectory
        mImageFiles = mImagesDir.listFiles();
//        Log.i(TAG, "onCreate: mImasize--->"+mImageFiles.length);
        Log.i(TAG, "onCreate: mNormalFiles--->"+mNormalfiles.length);

        for(File file : mNormalfiles){
            Log.i(TAG, "initData: filename-->"+file.getAbsolutePath());
            fileNameList.add(file.getName());
        }
    }

    private void initView() {
        fileRecyclerView = (RecyclerView)findViewById(R.id.files_recyclerView_list);
    }
}
