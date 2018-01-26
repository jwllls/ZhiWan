package com.hamitao.zhiwan.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.util.DataCleanUtil;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hamitao.zhiwan.Constant.USER_APK_LOCAL;
import static com.hamitao.zhiwan.Constant.USER_PIC_LOCAL;
import static com.hamitao.zhiwan.Constant.USER_RECORD_LOCAL;
import static com.hamitao.zhiwan.Constant.USER_VOICE_LOCAL;

/**
 * Created by linjianwen on 2018/1/4.
 */

public class SettingActivity extends BaseActivity {

    public static final String tag = SettingActivity.class.getSimpleName();


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.switch_push)
    Switch switchPush;


    DataCleanUtil cleanUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        title.setVisibility(View.VISIBLE);
        title.setText("设置");


        //初始化缓存路径
        cleanUtil = DataCleanUtil.getInstance();
        List<String> paths = new ArrayList<>();
        paths.add(USER_PIC_LOCAL);    //图片
        paths.add(USER_APK_LOCAL);    //安装包
        paths.add(USER_VOICE_LOCAL);  //视频
        paths.add(USER_RECORD_LOCAL); //录音
        cleanUtil.init(paths);
    }

    @OnClick({R.id.back, R.id.rl_close, R.id.rl_push, R.id.rl_cleanCache, R.id.rl_about, R.id.rl_helper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_close:
                startActivity(new Intent(this, TimingCloseActivity.class));
                break;
            case R.id.rl_push:
                if (switchPush.isChecked()) {
                    switchPush.setChecked(false);
//                    XGPushManager.unregisterPush(this);
                } else {
                    switchPush.setChecked(true);
//                    registerPush();
                }
                break;
            case R.id.rl_cleanCache:
                try {
                    new AlertDialog.Builder(this).setTitle("提示")
                            .setMessage("当前缓存：" + cleanUtil.getTotalCacheSize(this) + "\n清除缓存将清理应用中的所有数据！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cleanCache(SettingActivity.this);
                                }
                            }).setNegativeButton("取消", null).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.rl_helper:
                ToastUtil.showShort(this, "客服");
                break;
        }
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public void cleanCache(final Context context) {

        cleanUtil.cleanInternalCache(context);  //清除内部缓存
        cleanUtil.cleanExternalCache(context);  //清除外部存储
        cleanUtil.cleanCustomCache(USER_PIC_LOCAL); //清除图片
        cleanUtil.cleanCustomCache(USER_APK_LOCAL); //清除安装包
        cleanUtil.cleanCustomCache(USER_VOICE_LOCAL);  //清除音频
        cleanUtil.cleanCustomCache(USER_RECORD_LOCAL);  //清除录音

        ToastUtil.showShort(this, "清除成功");
    }


}
