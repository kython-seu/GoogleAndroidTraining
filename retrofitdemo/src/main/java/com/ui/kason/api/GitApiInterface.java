package com.ui.kason.api;

import com.ui.kason.entity.GitResult;
import com.ui.kason.entity.UserMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by kason_zhang on 9/2/2016.
 */
public interface GitApiInterface {
    @GET("/search/users")
    Call<GitResult> getUsersByName(@Query("q") String name);

    @POST("/user/create")
    Call<GitResult.ItemsBean> createUser(@Body String name, @Body String email);

    @PUT("/user/{id}/update")
    Call<GitResult.ItemsBean> updateUser(@Path("id") String id , @Body GitResult.ItemsBean user);

    @GET("/users/{user}")
    Call<UserMessage> getUserMessage(@Path("user") String user);
}
