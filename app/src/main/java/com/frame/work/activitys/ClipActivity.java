package com.frame.work.activitys;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.frame.work.R;
import com.frame.work.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
@TargetApi(19)
public class ClipActivity extends BaseActivity {
    private int a = 0;

    @BindView(R.id.ivCenter)
    ImageView ivCenter;

    ObjectAnimator objectAnimator,tranSlate,alpha;
    AnimatorSet animationSet;

    @Override
    public int innitLayout() {
        return R.layout.activity_clip;
    }


    @OnClick({R.id.iv_module,R.id.iv_start,R.id.iv_pause,R.id.iv_resume})
    public void onClick(View view) {
        Log.i(this.getClass().getName(),"state:"+objectAnimator.isStarted());
        Log.i(this.getClass().getName(),"state:"+objectAnimator.isRunning());
        Log.i(this.getClass().getName(),"state:"+objectAnimator.isPaused());


//        ___||___\\................///.........//
        switch (view.getId()) {
            case R.id.iv_module:
//                Intent intent=new Intent(ClipActivity.this, ThreadActivity.class);
//                startActivity(intent);
//                finish();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    if (objectAnimator != null && objectAnimator.isRunning()) {
//                        objectAnimator.pause();
//
//                    } else  {
//                        objectAnimator.resume();
//                    }
//                }
                animationSet.start();

                break;

            case R.id.iv_start:
                if(!objectAnimator.isRunning()){
                    objectAnimator.start();
                }
                break;
            case R.id.iv_pause:
                if(objectAnimator.isRunning()){
                    objectAnimator.pause();
                }
                break;
            case R.id.iv_resume:
                if(objectAnimator.isRunning()&&objectAnimator.isPaused()){
                    objectAnimator.resume();
                }
                break;
        }
    }

//    only test for git  A and E and C
}
