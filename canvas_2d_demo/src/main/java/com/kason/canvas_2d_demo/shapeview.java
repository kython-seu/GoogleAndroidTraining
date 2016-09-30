package com.kason.canvas_2d_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kason_zhang on 9/30/2016.
 */

public class shapeview extends View {
    private Paint mPaint;
    public shapeview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
    }

    public shapeview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制矩形
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(0,0,100,100);
        canvas.drawRect(rectF,mPaint);

        //回绘制点
        mPaint.setColor(Color.RED);
        canvas.drawPoint(150,0,mPaint);

        //绘制线
        canvas.drawLine(0,0,100,100,mPaint);

        //绘制多条直线
        mPaint.setColor(Color.GREEN);
        float[] lines = {
                0,0,100,100,
                100,100,200,250
        };
        canvas.drawLines(lines,mPaint);

        //绘制圆圈,圆心和半径
        canvas.drawCircle(100,100,50,mPaint);

        //绘制扇形
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(200,200,300,300,0,90,true,mPaint);
        }
        //绘制椭圆
        canvas.drawOval(300,400,400,700,mPaint);

        //绘制文字
        mPaint.setTextSize(80);
        canvas.drawText("Hello",300,500,mPaint);

        canvas.drawPosText("lik",new float[]{
            100,200,
            300,400,
            420,450
        },mPaint);

        //绘制Path
        Path path = new Path();
        path.moveTo(50,100);
        path.lineTo(100,200);
        path.lineTo(300,500);
        canvas.drawPath(path,mPaint);
    }
}
