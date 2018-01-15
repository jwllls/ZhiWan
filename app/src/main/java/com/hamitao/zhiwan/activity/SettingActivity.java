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
import com.hamitao.zhiwan.base.BaseActivity;
import com.hamitao.zhiwan.model.UserModel;
import com.hamitao.zhiwan.util.DataCleanUtil;
import com.hamitao.zhiwan.util.LogUtil;
import com.hamitao.zhiwan.util.ToastUtil;
import com.hamitao.zhiwan.util.UserUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushServiceV3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hamitao.zhiwan.Constant.USER_APK_LOCAL;
import static com.hamitao.zhiwan.Constant.USER_PIC_LOCAL;
import static com.hamitao.zhiwan.Constant.USER_RECORD_LOCAL;
import static com.hamitao.zhiwan.Constant.USER_VOICE_LOCAL;
import static com.hamitao.zhiwan.util.UserUtil.saveUserMine;

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
//                    ToastUtil.showShort(this, "关");
                    XGPushManager.unregisterPush(this);
                } else {
                    switchPush.setChecked(true);
//                    ToastUtil.showShort(this, "开");
                    registerPush();
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
        cleanUtil.cleanInternalCache(context);
        cleanUtil.cleanExternalCache(context);
        cleanUtil.cleanCustomCache(USER_PIC_LOCAL);
        cleanUtil.cleanCustomCache(USER_APK_LOCAL);
        cleanUtil.cleanCustomCache(USER_VOICE_LOCAL);
        cleanUtil.cleanCustomCache(USER_RECORD_LOCAL);
        ToastUtil.showShort(this, "清除成功");
    }


    UserModel model =new UserModel();


    //**************************************信鸽注册**************************************//
    public void registerPush() {
        //  信鸽注册
        if (UserUtil.user() != null) {
            model.setUserId(123456);
            saveUserMine(model);
            XGPushManager.registerPush(this, UserUtil.user().getUserId() + "",
                    new XGIOperateCallback() {
                        @Override
                        public void onSuccess(Object data, int fla) {
                            LogUtil.d(tag,UserUtil.user().getUserId() + "信鸽注册成功");
                        }

                        @Override
                        public void onFail(Object data, int errCode, String msg) {
                            LogUtil.d(tag,"信鸽注册失败---" + errCode + "---" + msg);
                        }
                    });
            Intent service = new Intent(getApplicationContext(), XGPushServiceV3.class);
            getApplicationContext().startService(service);
        }
    }
    //**************************************信鸽注册**************************************//


}
