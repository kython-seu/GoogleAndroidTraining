package com.ui.kason_zhang.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.ui.kason.api.GitApiInterface;
import com.ui.kason.entity.GitResult;
import com.ui.kason.rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GitApiInterface service = RestClient.getClient();
        Call<GitResult> call = service.getUsersByName("kython-seu");
        call.enqueue(new Callback<GitResult>() {
            @Override
            public void onResponse(Call<GitResult> call, Response<GitResult> response) {
                if(response.isSuccess()){
                    GitResult result = response.body();
                    Log.i(TAG, "response = "+ new Gson().toJson(result));
                    List<GitResult.ItemsBean> items = result.getItems();
                    Log.i(TAG, "Items = "+items.size());
                    for (GitResult.ItemsBean item : items){
                        Log.i(TAG, "every data: " +item);
                    }
                }
            }

            @Override
            public void onFailure(Call<GitResult> call, Throwable t) {

            }
        });
    }
}
