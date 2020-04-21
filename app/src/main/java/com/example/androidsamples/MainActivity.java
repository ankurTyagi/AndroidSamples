package com.example.androidsamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidsamples.servicesexamples.ForegroundService;
import com.example.androidsamples.servicesexamples.MyService;
import com.example.androidsamples.servicesexamples.jobscheduling.TestJobService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_start,btn_stop,btn_Start_frgrnd,btn_Stop_frgrnd,btn_Start_Schedular,btn_Stop_Schedular;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, MyService.class);

        btn_start = (Button)findViewById(R.id.btn_Start);
        btn_stop = (Button)findViewById(R.id.btn_Stop);
        btn_Start_frgrnd = (Button)findViewById(R.id.btn_Start_frgrnd);
        btn_Stop_frgrnd = (Button)findViewById(R.id.btn_Stop_frgrnd);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_Start_frgrnd.setOnClickListener(this);
        btn_Stop_frgrnd.setOnClickListener(this);
        btn_Start_frgrnd.setOnClickListener(this);
        btn_Stop_frgrnd.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Stop:
                stopService(intent);

                break;

            case R.id.btn_Start:
                startService(intent);
                break;
            case R.id.btn_Start_frgrnd:
                StartForgroundService();

                break;

            case R.id.btn_Stop_frgrnd:
                StopForgroundService();
                break;
            case R.id.btn_Start_Schedular:
                startScheduleJob(MainActivity.this);
                break;

            case R.id.btn_Stop_Schedular:
                stopScheduleJob(MainActivity.this);
                break;

        }
    }

    private void StartForgroundService() {

        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);

    }

    private void StopForgroundService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
    public static void startScheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, TestJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(123, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    public static void stopScheduleJob(Context context){
        JobScheduler jobScheduler =(JobScheduler)context.getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(123);
    }
}
