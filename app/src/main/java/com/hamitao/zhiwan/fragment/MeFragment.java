package com.hamitao.zhiwan.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.activity.CollectActivity;
import com.hamitao.zhiwan.activity.MachineActivity;
import com.hamitao.zhiwan.activity.MyRecordActivity;
import com.hamitao.zhiwan.activity.OrderActivity;
import com.hamitao.zhiwan.activity.ScheduleActivity;
import com.hamitao.zhiwan.activity.SettingActivity;
import com.hamitao.zhiwan.activity.UserInfoActivity;
import com.hamitao.zhiwan.adapter.MenuAdapter;
import com.hamitao.zhiwan.base.BaseFragment;
import com.hamitao.zhiwan.model.MenuModel;
import com.hamitao.zhiwan.util.ToastUtil;
import com.hamitao.zhiwan.view.HorizontialListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class MeFragment extends BaseFragment {
    private View view;
    private Unbinder unbinder;
    private Context context;
    private MenuAdapter adapter;
    private int[] drawables = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private String[] menuName = {"收藏", "下载", "我的录音", "最近播放","多出来得"};
    private List<MenuModel> list = new ArrayList<>();


    @BindView(R.id.face)
    CircleImageView face;
    @BindView(R.id.tv_signIn)
    TextView tvSignIn;
    @BindView(R.id.listView)
    HorizontialListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        context = getActivity();
        initMenu();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initMenu() {

        for (int i = 0; i < menuName.length; i++) {
            MenuModel model = new MenuModel();
            model.setDrawable(context.getDrawable(drawables[i]));
            model.setMenuName(menuName[i]);
            list.add(model);
        }
        adapter = new MenuAdapter(list,getActivity());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //收藏
                        startActivity(new Intent(getActivity(), CollectActivity.class));
                        break;
                    case 1:
                        //下载
                        break;
                    case 2:
                        //我的录音
                        startActivity(new Intent(getActivity(), MyRecordActivity.class));
                        break;
                    case 3:
                        //最近播放
                        break;
                }
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.face, R.id.tv_signIn, R.id.rl_machine, R.id.rl_schedule, R.id.rl_order, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.face:
                startActivity(new Intent(context, UserInfoActivity.class));
                break;
            case R.id.tv_signIn:
                ToastUtil.showShort(context, "已签到");
                break;
            case R.id.rl_machine:
                startActivity(new Intent(context, MachineActivity.class));
                break;
            case R.id.rl_schedule:
                startActivity(new Intent(context, ScheduleActivity.class));
                break;
            case R.id.rl_order:
                startActivity(new Intent(context, OrderActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(context, SettingActivity.class));
                break;
        }
    }


}
