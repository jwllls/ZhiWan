package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.adapter.MyScheduleAdapter;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的课表
 */
public class MyScheduleActivity extends BaseActivity implements BGAOnRVItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;
    @BindView(R.id.more)
    TextView more;

    private MyScheduleAdapter adapter;

    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("我的课表");
        more.setVisibility(View.VISIBLE);
        more.setText("新增课表");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    private void initData() {
        adapter = new MyScheduleAdapter(recyclerview);
        adapter.setOnRVItemClickListener(this);

        for (int i = 0; i < 5; i++) {
            list.add("课表" + i);
        }

        adapter.setData(list);

    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                startActivity(new Intent(this,EditScheduleActivity.class));
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        ToastUtil.showShort(this, "课表" + position);
    }
}
