package com.example.mvvmimplimentation.Views.MainActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmimplimentation.MainActivityAdapter.ArticleAdapter;
import com.example.mvvmimplimentation.Model.Article;
import com.example.mvvmimplimentation.R;
import com.example.mvvmimplimentation.RetrofitResponse.ArticleResponse;
import com.example.mvvmimplimentation.ViewModel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvmainactivity;
    private ProgressBar progressBarprogresscircular;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    private ArticleViewModel articleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inIt();
        getMovies();
    }

    private void inIt() {
        rcvmainactivity = findViewById(R.id.rcv_main_activity);
        progressBarprogresscircular = findViewById(R.id.progress_circular);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcvmainactivity.setLayoutManager(linearLayoutManager);

        rcvmainactivity.setHasFixedSize(true);
        articleAdapter = new ArticleAdapter(MainActivity.this, articleArrayList);
        rcvmainactivity.setAdapter(articleAdapter);
//        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
    }

    private void getMovies() {
        articleViewModel.getArticleResponseLiveData().observe(this, new Observer<ArticleResponse>() {
            @Override
            public void onChanged(ArticleResponse articleResponse) {
                if (articleResponse != null) {
                    progressBarprogresscircular.setVisibility(View.GONE);
                    List<Article> articles = articleResponse.getArticles();
                    Log.d("TAG", "onChanged: "+articles);
                    articleArrayList.addAll(articles);
                    articleAdapter.notifyDataSetChanged();

                }
            }
        });
    }
}