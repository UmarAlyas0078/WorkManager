package com.example.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.workmanager.Article.Article;
import com.example.workmanager.Article.ArticleResponse;
import com.example.workmanager.ArticleAdapter.ArticleAdapter;
import com.example.workmanager.RetrofitOperations.RetrofitRequest;
import com.example.workmanager.Room.ArticalDao;
import com.example.workmanager.Room.ArticalDatabase;
import com.example.workmanager.Utils.Constant;
import com.example.workmanager.Utils.NetworkConnection;
import com.example.workmanager.Worker.WorkHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_DESC = "task_desc";
    private static final String TAG = "MainActivity";
    private OneTimeWorkRequest oneTimeWorkRequest;
    private RecyclerView rcvmainactivity;
    private ProgressBar progressBarprogresscircular;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    private TextView text_View;
    private ArticalDatabase articalDatabase;
    private Article article;
    private LiveData<List<Article>> articleList;
    private ArticalDao articaleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_View = findViewById(R.id.textView);
        rcvmainactivity = findViewById(R.id.rcv_main_activity);
        progressBarprogresscircular = findViewById(R.id.progress_circular);
        articalDatabase = Room.databaseBuilder(MainActivity.this,
                ArticalDatabase.class,
                "Article")
                .allowMainThreadQueries()
                .build();
        articaleDao = articalDatabase.articalDao();
        progressBarprogresscircular.setVisibility(View.INVISIBLE);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcvmainactivity.setLayoutManager(linearLayoutManager);
        articleList = articaleDao.getArtical();
        articleList.observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                if (articleAdapter == null) {
                    articleAdapter = new ArticleAdapter(MainActivity.this, articles);
                    rcvmainactivity.setAdapter(articleAdapter);
                } else articleAdapter.setData(articles);


            }
        });
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
    }

    private Data createInputData(List<Article> articles) {
        /**
         * Passing Data to Work Manager
         */
        oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkHandler.class)
                //.setInputData(createInputData(articleList))
                .build();
        WorkManager.getInstance().
                enqueue(oneTimeWorkRequest);

        Data data = new Data.Builder()
                .putStringArray(TASK_DESC, articles.toArray(new String[articles.size()]))
                .build();
        return data;
    }

    public void deleteRoomData(View view) {
        articaleDao.deleteData();
    }

    public void callNetworkApi(View view) {
        progressBarprogresscircular.setVisibility(View.VISIBLE);
        if (NetworkConnection.isConnected(MainActivity.this)) {
            Retrofit retrofit = new Retrofit.Builder()
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
                        articalDatabase.articalDao().deleteData();
                        articalDatabase.articalDao().saveArtical(response.body().getArticles());
                    }

                }

                @Override
                public void onFailure(Call<ArticleResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure: " + t.getMessage());
                }
            });
        } else Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show();
    }
}
