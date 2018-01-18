package com.hamitao.zhiwan.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.activity.CollectActivity;
import com.hamitao.zhiwan.activity.MachineActivity;
import com.hamitao.zhiwan.activity.MyRecordActivity;
import com.hamitao.zhiwan.activity.MakeCardActivity;
import com.hamitao.zhiwan.activity.RecentPlayActivity;
import com.hamitao.zhiwan.activity.ScheduleActivity;
import com.hamitao.zhiwan.activity.SettingActivity;
import com.hamitao.zhiwan.activity.UserInfoActivity;
import com.hamitao.zhiwan.base.BaseFragment;
import com.hamitao.zhiwan.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by linjianwen on 2018/1/4.
 *
 * TAB 我的
 */

public class MeFragment extends BaseFragment {


    private View view;
    private Unbinder unbinder;
    private Context context;


    @BindView(R.id.face)
    CircleImageView face;
    @BindView(R.id.tv_signIn)
    TextView tvSignIn;


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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.face, R.id.tv_signIn, R.id.rl_machine, R.id.rl_schedule, R.id.rl_nfc, R.id.rl_setting, R.id.tv_collection, R.id.tv_record, R.id.tv_recentPlay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.face:
                startActivity(new Intent(context, UserInfoActivity.class));
                break;
            case R.id.tv_signIn:
                tvSignIn.setText("已签到");
                ToastUtil.showShort(context, "已签到");
                break;
            case R.id.rl_machine:
                startActivity(new Intent(context, MachineActivity.class));
                break;
            case R.id.rl_schedule:
                startActivity(new Intent(context, ScheduleActivity.class));
                break;
            case R.id.rl_nfc:
                startActivity(new Intent(context, MakeCardActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(context, SettingActivity.class));
                break;
            case R.id.tv_collection:
                //我的收藏
                startActivity(new Intent(getActivity(), CollectActivity.class));
                break;
            case R.id.tv_record:
                //我的录音
                startActivity(new Intent(getActivity(), MyRecordActivity.class));
                break;
            case R.id.tv_recentPlay:
                startActivity(new Intent(getActivity(), RecentPlayActivity.class));
                break;
        }
    }


}
