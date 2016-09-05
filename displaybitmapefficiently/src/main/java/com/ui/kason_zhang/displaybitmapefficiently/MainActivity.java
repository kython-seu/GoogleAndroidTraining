package com.ui.kason_zhang.displaybitmapefficiently;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView imageView;
    private ImageView secondImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secondImage=(ImageView)findViewById(R.id.secondImage);
        secondImage.setImageResource(R.drawable.flower);
        imageView = (ImageView)findViewById(R.id.myImage);


        //getImageByTargetSize();
        //BitMapWorkTask bitMapWorkTask = new BitMapWorkTask()
        int resId= R.drawable.flower;
        loadBitmap(resId,MainActivity.this,imageView);
    }
    public void loadBitmap(int resId, Activity activity,ImageView imageView) {
        BitMapWorkTask bitMapWorkTask = new BitMapWorkTask(activity,imageView);
        bitMapWorkTask.execute(resId);
    }
    private void getImageByTargetSize() {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;//set the image not into the internal memory;
        BitmapFactory.decodeResource(getResources(), R.drawable.flower, mOptions);
        int imageHeight = mOptions.outHeight;
        int imageWidth = mOptions.outWidth;
        String imageType = mOptions.outMimeType;
        Log.i(TAG, "onCreate: imageHeight:"+imageHeight+" --imageWidth:"+imageWidth
        +"---imageType:"+imageType);
        int sampleSize = calculateSampleSize(mOptions,100,100);
        Log.i(TAG, "onCreate: the sampleSize is"+sampleSize);

        mOptions.inSampleSize = sampleSize;
        mOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flower, mOptions);

        Log.i(TAG, "onCreate: "+bitmap.getHeight()+"-----Option Getheight:"+mOptions.outHeight);
        imageView.setImageBitmap(bitmap);
    }

    private int calculateSampleSize(BitmapFactory.Options option,
                                    int targetHeight,int tartgetWidth){
        int sampleSize = 1;
        int height = option.outHeight;
        int width = option.outWidth;

        if(height>targetHeight||width>tartgetWidth){
            final int halfheight = height/2;
            final int halfwidth = width/2;
            while(halfheight/sampleSize>targetHeight&&halfwidth/sampleSize>tartgetWidth){
                Log.i(TAG, "calculateSampleSize: "+halfheight/sampleSize+" : "+targetHeight
                +"-----"+halfwidth/sampleSize+" : "+tartgetWidth);
                sampleSize = sampleSize*2;
            }
        }

        return sampleSize;
    }
}
