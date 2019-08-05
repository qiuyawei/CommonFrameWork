package com.frame.work.base;

import android.app.Application;

/**
 * app 入口
 *
 * 在此类不能做太多耗时操作，不然影响app启动时间
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
