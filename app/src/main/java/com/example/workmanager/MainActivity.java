package com.example.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.example.workmanager.Article.Article;
import com.example.workmanager.ArticleAdapter.ArticleAdapter;
import com.example.workmanager.Room.ArticalDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_DESC = "task_desc";
    private static final String TAG = "MainActivity";
    private Data data;
    private OneTimeWorkRequest oneTimeWorkRequest;
    private RecyclerView rcvmainactivity;
    private ProgressBar progressBarprogresscircular;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    private TextView text_View;
    private ArticalDatabase articalDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_View = findViewById(R.id.textView);
        rcvmainactivity = findViewById(R.id.rcv_main_activity);
        progressBarprogresscircular = findViewById(R.id.progress_circular);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcvmainactivity.setLayoutManager(linearLayoutManager);
        /**
         * to pass data from views to worker class
         */
   /*     data = new Data.Builder()
                .putString(TASK_DESC, "Helo this is message")
                .build();*/
        /**
         * these are WorkRequest subclasses to perfrom tha task on time or periodically
         */
     /*   oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkHandler.class)
                // .setInputData(data)
                .build();*/
        /**
         * Listner
         */
   /*     WorkManager.getInstance().
                enqueue(oneTimeWorkRequest);

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {
                            progressBarprogresscircular.setVisibility(View.INVISIBLE);
                            String data1 = workInfo.getOutputData().getString(KEY_TASK_DESC);
                            text_View.append(workInfo.getState() + "\n");
                            Log.d("TAG", "onChanged: " + data1);
                            Toast.makeText(MainActivity.this, "" + data1, Toast.LENGTH_SHORT).show();
                        }
//                        Data data1 = workInfo.getOutputData();
//                        String outPut = data1.getString(KEY_TASK_DESC);
//                        Toast.makeText(MainActivity.this, "" + outPut, Toast.LENGTH_SHORT).show();

                    }
                });*/

        /**
         * fetching user response
         */
     /*   Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitRequest retrofitRequest = retrofit.create(RetrofitRequest.class);
        Call<ArticleResponse> articleResponseCall = retrofitRequest.getResponse(Constant.QUERY, Constant.API_KEY);
        articleResponseCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful()) {
                    progressBarprogresscircular.setVisibility(View.INVISIBLE);
                    ArticleResponse articleResponse = response.body();
                    //   List<Article> articleList = articleResponse.getArticles();
                    for (int i = 0; i < articleResponse.getArticles().size(); i++) {
                        String title = articleResponse.getArticles().get(i).getTitle();
                        String auther = articleResponse.getArticles().get(i).getAuthor();
                        String publisher = articleResponse.getArticles().get(i).getPublishedAt();
                        String description = articleResponse.getArticles().get(i).getDescription();
                        String imageUrl = articleResponse.getArticles().get(i).getUrlToImage();
                        Article article = new Article(auther, title, description, "", imageUrl, publisher);
                        articalDatabase = Room.databaseBuilder(MainActivity.this, ArticalDatabase.class, "Article")
                                .allowMainThreadQueries().build();
                        articalDatabase.articalDao().saveArtical(article);
                    }
                    articleAdapter = new ArticleAdapter(MainActivity.this, articleResponse.getArticles());
                    rcvmainactivity.setAdapter(articleAdapter);
                    rcvmainactivity.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
*/
        progressBarprogresscircular.setVisibility(View.VISIBLE);
        dataBaseObj();
        List<Article> articleList = articalDatabase.articalDao().getArtical();
        if (articleList != null) {
            progressBarprogresscircular.setVisibility(View.INVISIBLE);
            articleAdapter = new ArticleAdapter(MainActivity.this, articleList);
            rcvmainactivity.setAdapter(articleAdapter);
            rcvmainactivity.setHasFixedSize(true);
            Log.d(TAG, "onRoom: " + articleList);
        }


    }

    public void dataBaseObj() {
        articalDatabase = Room.databaseBuilder(MainActivity.this, ArticalDatabase.class, "Article")
                .allowMainThreadQueries().build();
    }
}
