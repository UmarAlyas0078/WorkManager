package com.example.workmanager.Worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.workmanager.Article.ArticleResponse;
import com.example.workmanager.R;
import com.example.workmanager.RetrofitOperations.RetrofitRequest;
import com.example.workmanager.Utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.workmanager.MainActivity.TASK_DESC;

public class WorkHandler extends Worker {
    public static final String KEY_TASK_DESC = "key_task_desc";
    private static final String TAG = "WorkHandler";
    public WorkHandler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String taskDesk = getInputData().getString(TASK_DESC);
        Log.d(TAG, "doWork: " + taskDesk);
        notificationData("WorkManager", taskDesk);
        //Data outputData = outPutData(KEY_TASK_DESC, "Hello There From Output");
        // getMoviesData(Constant.QUERY, Constant.API_KEY);
        return Result.success();
    }

    public void getMoviesData(String q, String apiKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitRequest retrofitRequest = retrofit.create(RetrofitRequest.class);
        Call<ArticleResponse> articleResponseCall = retrofitRequest.getResponse(q, apiKey);
        articleResponseCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.isSuccessful()) {
                    // liveData.postValue(response.body());
                    ArticleResponse articleResponse = response.body();
                    Log.d("TAG", "onResponse: " + articleResponse.getStatus() + articleResponse.getArticles() + "Total Result:" + articleResponse.getTotalResults());
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });
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
