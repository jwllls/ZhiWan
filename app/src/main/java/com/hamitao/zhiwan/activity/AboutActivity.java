package com.hamitao.zhiwan.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hamitao.zhiwan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by linjianwen on 2018/1/5.
 */

public class AboutActivity extends AppCompatActivity {

    PackageInfo packageInfo;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.app_version)
    TextView app_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {


        try {
            packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            app_version.setText("V" + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        title.setVisibility(View.VISIBLE);
        title.setText("关于");
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:

                break;
        }
    }
}
