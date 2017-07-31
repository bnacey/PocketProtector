package com.bnacey.apps.pocketprotector;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class ProtectorActivity extends AppCompatActivity {

    int delay = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        boolean taskExists = false;
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();

        for (final ActivityManager.AppTask task : tasks) {
            if (new ComponentName(getApplication(), MainActivity.class).equals(task.getTaskInfo().baseIntent.getComponent())) {
                taskExists = true;
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        task.moveToFront();
                    }
                }, delay);
                Log.d("TAG", "onResume: ");
            }
        }
        if (!taskExists) {
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(ProtectorActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }, delay);
        }
//        finish();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
//        new Handler(getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }, 500);
        finish();
    }
}
