package com.ui.kason_zhang.displaybitmapefficiently;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by kason_zhang on 9/5/2016.
 */
public class BitMapWorkTask extends AsyncTask<Integer,Void,Bitmap>{

    private WeakReference<ImageView> imageViewWeakReference;
    private WeakReference<Activity> activityWeakReference;
    private int data;
    public BitMapWorkTask(Activity activity,ImageView imageView){
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
        activityWeakReference = new WeakReference<Activity>(activity);
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(imageViewWeakReference!=null&&bitmap!=null){
            ImageView imageView = imageViewWeakReference.get();
            if(imageView!=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        Activity activity = activityWeakReference.get();
        if(activity == null)
            return null;
        return decodeSampledBitmapFromResource(activity.getResources(), data, 100, 100);
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
