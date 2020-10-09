package com.example.workmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.workmanager.Worker.WorkHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkHandler.class).build();

        findViewById(R.id.btn_notify_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Helo", Toast.LENGTH_SHORT).show();
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }
        });
    }
}