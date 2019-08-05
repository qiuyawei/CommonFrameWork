package com.frame.work;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.frame.work.callBack.ServiceCallBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyBackGroundService extends Service {
    private String TAG = "BackGroundService";
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Random mRandom = new Random();
    private List<Class> classes=new ArrayList<>();



    public void setmServiceCallBack(ServiceCallBack mServiceCallBack) {
        this.mServiceCallBack = mServiceCallBack;
    }

    private ServiceCallBack mServiceCallBack;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate_service has start");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        startTask();
        return new MyBinder();
    }



    public class MyBinder extends Binder {
        public MyBackGroundService getMyBackGroundService(){
            return MyBackGroundService.this;
        }
    }



    public void startTask(){
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String time = sdf.format(new Date());
                int value = mRandom.nextInt(10000);
                Log.i(TAG, "msg:" + time + "_" + value);
                if (mServiceCallBack != null)
                    mServiceCallBack.sendMessage(time + "_" + value);

            }
        }, 1000, 5000, TimeUnit.MILLISECONDS);
    }
}
