package com.example.mvvmimplimentation.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmimplimentation.Data.Remote.ArticleRepository;
import com.example.mvvmimplimentation.RetrofitResponse.ArticleResponse;

import static com.example.mvvmimplimentation.Util.Constant.API_KEY;
import static com.example.mvvmimplimentation.Util.Constant.QUERY;

public class ArticleViewModel extends AndroidViewModel {
    private ArticleRepository articleRepository;
    private LiveData<ArticleResponse> articleResponseLiveData;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        articleRepository = new ArticleRepository();
        this.articleResponseLiveData = articleRepository.getMoviesData(QUERY, API_KEY);
    }

    public LiveData<ArticleResponse> getArticleResponseLiveData() {
        return articleResponseLiveData;
    }
}
