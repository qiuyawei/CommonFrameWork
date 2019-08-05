package com.frame.work;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frame.work.activitys.ThreadActivity;
import com.frame.work.adapters.ActivtyAdapter;
import com.frame.work.base.BaseActivity;
import com.frame.work.beans.ActivityBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private List<ActivityBean> activtyBeans=new ArrayList<>();
    private ActivtyAdapter activtyAdapter;

    @BindView(R.id.rvToDoList)
    RecyclerView recyclerView;
    @Override
    public int innitLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(activtyBeans.isEmpty()){
            activtyBeans.add(new ActivityBean(ThreadActivity.class.getName(),getString(R.string.thread_activity_desc)));
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getmContext());
            recyclerView.addItemDecoration(new DividerItemDecoration(getmContext(),DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(linearLayoutManager);

            activtyAdapter=new ActivtyAdapter(R.layout.item_activity_list,activtyBeans);
            recyclerView.setAdapter(activtyAdapter);
        }
    }
}
