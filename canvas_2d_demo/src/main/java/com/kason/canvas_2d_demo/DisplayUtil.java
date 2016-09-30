package com.kason.canvas_2d_demo;

import android.content.Context;

/**
 * Created by kason_zhang on 9/30/2016.
 */

public class DisplayUtil {
    public static int px2dp(Context context,float pxvalue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxvalue/scale+0.5f);
    }
}
