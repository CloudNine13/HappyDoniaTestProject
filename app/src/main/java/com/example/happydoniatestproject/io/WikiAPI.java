package com.example.happydoniatestproject.io;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface WikiAPI {
    @GET
    Call<ResponseBody> getNearbyArticles(
            @Url String url
    );
}
