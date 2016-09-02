package com.ui.kason.rest;

import com.ui.kason.api.GitApiInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kason_zhang on 9/2/2016.
 */
public class RestClient {

    private static final String BASE_URL = "https://api.github.com";

    public static GitApiInterface getClient(){
        GitApiInterface gitApiInterface = null;
        //get the OkHttpClient Object
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response;
            }
        }).build();

        //get Retrofit Object
        Retrofit client = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitApiInterface = client.create(GitApiInterface.class);
        return gitApiInterface;
    }
}
