package trendmicro.okhttpntlm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //OkHttpClient okHttpClient = new OkHttpClient().
                    //authenticator(new ProxyNTLMAuthenticator
                            //("kason_zhang", "zk_19921115", "trend"));

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setAuthenticator(new NTLMAuthenticator
                    ("kason_zhang", "zk_19921115", "trend"));
            final Request request = new Request.Builder().url(
                    "http://phvm-is-baweb01.ph.trendnet.org:8089/api/search/2016-08-24/any/true/3")
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.i(TAG, "Request got a error "+e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if(response.isSuccessful()){
                        String str = response.body().toString();
                        Log.i(TAG, "onResponse: ----------*****"+str);
                    }
                }
            });
            /*okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "Request got a error "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        String str = response.body().toString();
                        Log.i(TAG, "onResponse: ----------*****"+str);
                    }
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //client.setAuthenticator(new NTLMAuthenticator(usr, pwd, dom));
    }
}
