package com.example.mvvmimplimentation.Data.Remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmimplimentation.RetrofitOperaion.RetrofitRequest;
import com.example.mvvmimplimentation.RetrofitOperaion.RetrofitService;
import com.example.mvvmimplimentation.RetrofitResponse.ArticleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private static final String TAG = "ArticleRepository";
    private RetrofitRequest request;

    public ArticleRepository() {
        request = RetrofitService.getRetrofit().create(RetrofitRequest.class);
    }

    public LiveData<ArticleResponse> getMoviesData(String q, String apiKey) {
        final MutableLiveData<ArticleResponse> data = new MutableLiveData<>();
        request.getResponse(q, apiKey).enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
