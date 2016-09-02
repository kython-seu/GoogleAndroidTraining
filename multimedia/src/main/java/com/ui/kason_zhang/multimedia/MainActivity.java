package com.ui.kason_zhang.multimedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_VIDDEO = 2;
    String mCurrentPhotoPath;   //current captured picture path
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = (VideoView)findViewById(R.id.videowiew);

    }
    private File createImageFile() throws IOException {
        /*File mypictureDir = getExternalFilesDir("mypicture");
        if(!mypictureDir.exists()){
            Log.i(TAG, "onCreate: new the directory");
            mypictureDir.mkdirs();
        }else{
            Log.i(TAG, "onCreate: has the directory");
        }*/
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir("mypicture");
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }else{
            Log.i(TAG, "onCreate: has the directory");
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.i(TAG, "createImageFile: current photo path is: "+mCurrentPhotoPath);
        return image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,100,1,"Camera");
        menu.add(1,101,1,"video");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                dispatchTakePictureIntent();
                break;
            case 101:
                dispatchTakeVideoIntent();
            default:
                break;
        }
        return true;
    }

    private void dispatchTakeVideoIntent() {
        Intent requestVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(requestVideoIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(requestVideoIntent,REQUEST_VIDDEO);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.ui.kason_zhang.multimedia.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //galleryAddPic();
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    //add the picture to the gallary
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.i(TAG, "onActivityResult: has alredy captured the picture");
            //galleryAddPic();
            //Log.i(TAG, "onActivityResult: mCurrentPhotoPath--->"+mCurrentPhotoPath);
        }
        if(requestCode == REQUEST_VIDDEO && resultCode == RESULT_OK){
            Log.i(TAG, "onActivityResult: video has been taken");
            //VideoView mVideoView = new VideoView(MainActivity.this);
            Uri videoUri = intent.getData();
            Log.i(TAG, "onActivityResult: pth is "+videoUri.getPath());
            if(videoUri != null) {
                Toast.makeText(MainActivity.this,"not null",Toast.LENGTH_LONG).show();
                mVideoView.setVideoURI(videoUri);
                MediaController mediaController = new MediaController(MainActivity.this);
                mVideoView.setMediaController(mediaController);

                mVideoView.requestFocus();
                mVideoView.start();
            }else{
                Toast.makeText(MainActivity.this,"hehehe null",Toast.LENGTH_LONG).show();
            }
        }

    }

}
