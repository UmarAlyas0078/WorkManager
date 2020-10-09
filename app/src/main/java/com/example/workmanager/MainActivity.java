package com.example.workmanager;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.workmanager.Worker.WorkHandler;

import static com.example.workmanager.Worker.WorkHandler.KEY_TASK_DESC;

public class MainActivity extends AppCompatActivity {
    public static final String TASK_DESC = "task_desc";
    private Data data;
    private RecyclerView recyclerView;
    private OneTimeWorkRequest oneTimeWorkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcv_main_activity);
        /**
         * to pass data from views to worker class
         */
        data = new Data.Builder()
                .putString(TASK_DESC, "Helo this is message")
                .build();

        /**
         * these are WorkRequest subclasses to perfrom tha task on time or periodically
         */
        oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkHandler.class)
                .setInputData(data)
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
                        Data data1 = workInfo.getOutputData();
                        String outPut = data1.getString(KEY_TASK_DESC);
                        Toast.makeText(MainActivity.this, "" + outPut, Toast.LENGTH_SHORT).show();
                        // textView.append(workInfo.getState() + "\n");
                    }
                });
    }
}
