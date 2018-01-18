package com.hamitao.zhiwan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.activity.SelectContactActivity;
import com.hamitao.zhiwan.adapter.ChatGroupAdapter;
import com.hamitao.zhiwan.base.BaseFragment;
import com.hamitao.zhiwan.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class WechatFragment extends BaseFragment {

    View view;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    Unbinder unbinder;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.tv_add_machine)
    TextView tvAddMachine;
    @BindView(R.id.tv_add_group)
    TextView tvAddGroup;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.tv_none)
    TextView tvNone;

    private ChatGroupAdapter adapter;


    private SelectContactActivity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wechat, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        adapter = new ChatGroupAdapter(recyclerview);
//
//        activity = new SelectContactActivity();
//
//        activity.setCallback(new SelectContactActivity.FinishCallback() {
//            @Override
//            public void onFinish(String groupName) {
//                adapter.addItem(adapter.getItemCount(), groupName);
//            }
//        });

    }

    private void initView() {
        back.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        title.setText("微聊");
        more.setVisibility(View.VISIBLE);
        more.setText("联系人");
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);
        List<String> name = new ArrayList<>();
        name.add("宝宝的家庭组");
        name.add("宝宝");
        adapter.setData(name);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.more, R.id.tv_add_machine, R.id.tv_add_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more:
                break;
            case R.id.tv_add_machine:
                ToastUtil.showShort(getActivity(), "添加设备");
                break;
            case R.id.tv_add_group:
//                startActivity();
                startActivityForResult(new Intent(getActivity(), SelectContactActivity.class),101);
                break;
        }
    }


    public void setGroupName(String groupName) {
        adapter.addItem(adapter.getItemCount(),groupName);
    }
}
