package com.hamitao.zhiwan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chenenyu.router.annotation.Route;
import com.hamitao.zhiwan.R;
import com.zhiwan.hamitao.base_module.base.BaseActivity;

/**
 * Created by linjianwen on 2018/1/4.
 */
@Route("timing_close")
public class TimingCloseActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timingclose);
    }
}
