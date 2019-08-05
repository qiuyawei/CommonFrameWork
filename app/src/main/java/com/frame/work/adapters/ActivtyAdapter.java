package com.frame.work.adapters;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.work.R;
import com.frame.work.beans.ActivityBean;

import java.util.List;

public class ActivtyAdapter extends BaseQuickAdapter<ActivityBean, BaseViewHolder> {

    public ActivtyAdapter(int layoutResId, @Nullable List<ActivityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityBean item) {
        helper.setText(R.id.tvActivityName,item.getActivityName());
        helper.setText(R.id.tvActivityDesc,item.getActivityDesc());
    }
}
