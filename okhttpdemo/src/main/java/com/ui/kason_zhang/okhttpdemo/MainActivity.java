package com.ui.kason_zhang.okhttpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final int OKHTTP_GET = 0;
    private static final int OKHTTP_POST = 1;
    @Bind(R.id.text)
    TextView text;

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case OKHTTP_GET:
                        text.setText((String)msg.obj);
                        break;
                    case OKHTTP_POST:
                        text.setText((String)msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(1,100,1,"okHttpGet");
        menu.add(1,101,1,"okHttpPost");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 100:
                okHttpGet();
                break;
            case 101:
                okHttpPost();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void okHttpPost(){
        OkHttpClient okHttpClient = new OkHttpClient();
        final MediaType Media_Type_TEXT = MediaType.parse("text/plain");
        final String postBody = "Hello World";
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return Media_Type_TEXT;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(postBody);
            }
        };

        Request request = new Request.Builder().url("http://www.baidu.com")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: error message "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = OKHTTP_POST;
                message.obj = response.body().toString();
                Log.i(TAG, "onResponse: ---"+response.body().toString());
                mHandler.sendMessage(message);
            }
        });
        /*try {
            Response response = okHttpClient.newCall(request).execute();
            if(!response.isSuccessful()){
                Log.i(TAG, "okHttpPost: server error");
            }else{
                Log.i(TAG, "okHttpPost: "+response.body().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    private void okHttpHeader() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://github.com")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                .addHeader("Accept","text/html")
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!response.isSuccessful()){
            Log.i(TAG, "okHttpHeader: the server got errors");
        }else{
            Log.i(TAG, "okHttpHeader: port "+response.header("Server"));
            Log.i(TAG, "okHttpHeader: "+response.headers("Set-Cookie"));
        }
    }

    private void okHttpGet() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request  = new Request.Builder().url("https://github.com").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: error message "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = OKHTTP_GET;
                message.obj = response.body().toString();
                Log.i(TAG, "onResponse: "+response.isSuccessful());
                Log.i(TAG, "onResponse: ---"+response.body().toString());
                mHandler.sendMessage(message);
            }
        });
    }
}
