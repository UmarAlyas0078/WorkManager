package com.example.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.example.workmanager.Article.Article;
import com.example.workmanager.Article.ArticleResponse;
import com.example.workmanager.ArticleAdapter.ArticleAdapter;
import com.example.workmanager.RetrofitOperations.RetrofitRequest;
import com.example.workmanager.Utils.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.workmanager.Utils.Constant.API_KEY;
import static com.example.workmanager.Utils.Constant.QUERY;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_DESC = "task_desc";
    private Data data;
    private OneTimeWorkRequest oneTimeWorkRequest;
    private RecyclerView rcvmainactivity;
    private ProgressBar progressBarprogresscircular;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Article> articleArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inIt();
//        /**
//         * to pass data from views to worker class
//         */
////        data = new Data.Builder()
////                .putString(TASK_DESC, "Helo this is message")
////                .build();
//        /**
//         * these are WorkRequest subclasses to perfrom tha task on time or periodically
//         */
//        oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkHandler.class)
//                //.setInputData(data)
//                .build();
//        /**
//         * Listner
//         */
//        WorkManager.getInstance().
//                enqueue(oneTimeWorkRequest);
//
//        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
//                .observe(this, new Observer<WorkInfo>() {
//                    @Override
//                    public void onChanged(WorkInfo workInfo) {
//                        Data data1 = workInfo.getOutputData();
//                        String outPut = data1.getString(KEY_TASK_DESC);
//                        Toast.makeText(MainActivity.this, "" + outPut, Toast.LENGTH_SHORT).show();
//                        // textView.append(workInfo.getState() + "\n");
//                    }
//                });
    }

    private void inIt() {
        rcvmainactivity = findViewById(R.id.rcv_main_activity);
        progressBarprogresscircular = findViewById(R.id.progress_circular);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcvmainactivity.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitRequest retrofitRequest = retrofit.create(RetrofitRequest.class);
        Call<ArticleResponse> articleResponseCall = retrofitRequest.getResponse(QUERY, API_KEY);
        articleResponseCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful()) {
                    progressBarprogresscircular.setVisibility(View.INVISIBLE);
                    ArticleResponse articleResponse = response.body();
                    String total = articleResponse.getStatus();
                    Log.d("TAG", "onResponse: " + total);
                    articleAdapter = new ArticleAdapter(MainActivity.this, articleResponse.getArticles());
                    rcvmainactivity.setAdapter(articleAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });
        rcvmainactivity.setHasFixedSize(true);

        // articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        //articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
    }
}
