package com.frame.work.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(innitLayout());
        ButterKnife.bind(this);
    }


    /**
     * @return activity layout
     */
    public abstract int innitLayout();



    public Context getmContext() {
        return mContext;
    }
}
