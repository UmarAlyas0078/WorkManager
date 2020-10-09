package com.example.workmanager.Worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.workmanager.R;

import static com.example.workmanager.MainActivity.TASK_DESC;

public class WorkHandler extends Worker {
    public static final String KEY_TASK_DESC = "key_task_desc";

    public WorkHandler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String taskDesk = getInputData().getString(TASK_DESC);
        notificationData("WorkManager", taskDesk);
        Data outputData = outPutData(KEY_TASK_DESC, "Hello There From Output");
        return Result.success(outputData);
    }

    private Data outPutData(String key, String outputMessage) {
        Data data = new Data.Builder()
                .putString(key, outputMessage)
                .build();
        return data;
    }

    private void notificationData(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("codes", "codes", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getApplicationContext(), "codes")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.ic_launcher_background);
        notificationManager.notify(1, notificationCompat.build());

    }
}
