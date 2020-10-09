package com.example.workmanager.RetrofitOperations;


import com.example.workmanager.Article.ArticleResponse;

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
