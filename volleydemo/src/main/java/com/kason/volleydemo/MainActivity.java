package com.kason.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Button button3;
    private ImageView imageView;
    private static final String TAG = "MainActivity";
    ImageLoader mImageLoader;
    NetworkImageView mNetworkImageView;
    private static final String IMAGE_URL =
            "http://developer.android.com/images/training/system-ui.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.imageView);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest("https://www.baidu.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: "+response.substring(0,500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: Volley Error"+error.getMessage());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest);
            }
        });
        String url = "http://i.imgur.com/7spzG.png";
        final ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
                Log.i(TAG, "onResponse: Image Request Successfully");
            }
        },0, 0, null,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: Image Request Error"+error.getMessage());
            }
        });
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(imageRequest);
            }
        });
        mImageLoader = new ImageLoader(requestQueue
                ,new LruBitmapCache(LruBitmapCache.getCacheSize(this)));
        mNetworkImageView = (NetworkImageView)findViewById(R.id.networkImageView);
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNetworkImageView.setImageUrl(IMAGE_URL,mImageLoader);
            }
        });


    }
}
