package com.kason.layoutinflaterdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by kason_zhang on 9/18/2016.
 */
public class MyView extends View {
    private static final String TAG = "MyView";
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "widthMeasureSpec: "+widthMeasureSpec+" heightMeasureSpec: "+heightMeasureSpec);
        setMeasuredDimension(getMySize(widthMeasureSpec),
                getMySize(heightMeasureSpec));
    }
    public int getMySize(int measureSpec){
        Log.i(TAG, "measureSpec: "+measureSpec);
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        Log.i(TAG, "mode: "+mode+" size: "+size);
        if(mode == MeasureSpec.EXACTLY){
            //specify the size of the view
            result = size;
        }else{
            Log.i(TAG, "getMySize: ---------->not measureSpec EXACTLY");
            result = 200;//another two mode:AT_MOST AND UNSPECIFIED;set the default size equals 200px;
            if(mode == MeasureSpec.AT_MOST){
                result = Math.min(result,size);
            }
        }
        Log.i(TAG, "result: "+result);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorAccent));
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        canvas.drawLine(0,0,50,50,mPaint);

        canvas.drawRect(10,10,150,150,mPaint);
    }
}
