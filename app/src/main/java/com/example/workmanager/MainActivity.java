package com.example.workmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.workmanager.Article.Article;
import com.example.workmanager.ArticleAdapter.ArticleAdapter;
import com.example.workmanager.Worker.WorkHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_DESC = "task_desc";
    private Data data;
    private OneTimeWorkRequest oneTimeWorkRequest;
    private RecyclerView rcvmainactivity;
    private ProgressBar progressBarprogresscircular;
    private ArticleAdapter articleAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    private TextView text_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_View = findViewById(R.id.textView);
        rcvmainactivity = findViewById(R.id.rcv_main_activity);
        progressBarprogresscircular = findViewById(R.id.progress_circular);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcvmainactivity.setLayoutManager(linearLayoutManager);
        //articleAdapter = new ArticleAdapter(MainActivity.this, articleResponse.getArticles());
        rcvmainactivity.setAdapter(articleAdapter);
        rcvmainactivity.setHasFixedSize(true);
        /**
         * to pass data from views to worker class
         */
        /*data = new Data.Builder()
                .putString(TASK_DESC, "Helo this is message")
                .build();*/
        /**
         * these are WorkRequest subclasses to perfrom tha task on time or periodically
         */
        oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkHandler.class)
                // .setInputData(data)
                .build();
        /**
         * Listner
         */
        WorkManager.getInstance().
                enqueue(oneTimeWorkRequest);

        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {
                            progressBarprogresscircular.setVisibility(View.INVISIBLE);
                            Data data1 = workInfo.getOutputData();
                            text_View.append(workInfo.getState() + "\n");
                            Toast.makeText(MainActivity.this, "" + workInfo.getState().name(), Toast.LENGTH_SHORT).show();
                        }
//                        Data data1 = workInfo.getOutputData();
//                        String outPut = data1.getString(KEY_TASK_DESC);
//                        Toast.makeText(MainActivity.this, "" + outPut, Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
