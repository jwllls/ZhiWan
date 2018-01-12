package com.hamitao.zhiwan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.base.BaseFragment;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class WechatFragment extends BaseFragment {

    View view ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_wechat,container,false);
        return view;
    }
}
