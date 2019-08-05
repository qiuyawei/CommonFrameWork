package com.frame.work.activitys;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.frame.work.MyBackGroundService;
import com.frame.work.R;
import com.frame.work.base.BaseActivity;
import com.frame.work.callBack.ServiceCallBack;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceActivity extends BaseActivity implements ServiceCallBack {
    @BindView(R.id.tv_get_service)
    TextView tvGetValue;
    MyBackGroundService myBackGroundService;
    private ServiceConnection mServiceConnection;

    private String TAG = "ServiceActivity";

    @Override
    public int innitLayout() {
        return R.layout.activity_service;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected:" + name.getPackageName());
                MyBackGroundService.MyBinder myBinder = (MyBackGroundService.MyBinder) service;

                myBackGroundService = myBinder.getMyBackGroundService();
                myBackGroundService.setmServiceCallBack(ServiceActivity.this::sendMessage);
               /* if(service instanceof MyBackGroundService.MyBinder){
                    Log.i("TAG","==");
                }else {
                    Log.i("TAG","=!=");

                }*/
//                myBackGroundService= ((MyBackGroundService.MyBinder)service).getMyBackGroundService();
//                myBackGroundService.setmServiceCallBack(ServiceActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected");
                myBackGroundService.setmServiceCallBack(null);
                myBackGroundService = null;
            }

            @Override
            public void onBindingDied(ComponentName name) {
                Log.i(TAG, "onBindingDied");

            }
        };
    }

    Intent intent;

    @OnClick({R.id.bt_service, R.id.bt_service_unbinder})
    public void onClick(View view) {
        if (view.getId() == R.id.bt_service) {
            intent = new Intent("com.bind.myservice");
            intent.setPackage(getPackageName());
            Log.i(TAG, "bindResult:" + bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE));
        } else if (view.getId() == R.id.bt_service_unbinder) {
            try {
//                unbindService(mServiceConnection);
//                myBackGroundService.setmServiceCallBack(null);
//                myBackGroundService=null;
                Log.i(TAG, "stopService:" + stopService(intent));
            } catch (Exception e) {
                Log.i(TAG, "unbind exception:" + e.getMessage());
            }
        }
    }

    @Override
    public void sendMessage(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvGetValue.setText(msg);
            }
        });
    }
}
