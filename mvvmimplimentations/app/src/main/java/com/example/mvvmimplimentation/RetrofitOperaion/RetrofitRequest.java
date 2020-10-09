package com.example.mvvmimplimentation.RetrofitOperaion;

import com.example.mvvmimplimentation.RetrofitResponse.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitRequest {
    @GET("v2/everything/")
    Call<ArticleResponse> getResponse(
            @Query("q") String q,
            @Query("apiKey") String apiKey
    );
}
