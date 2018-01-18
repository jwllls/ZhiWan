package com.hamitao.zhiwan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.model.UserModel;
import com.hamitao.zhiwan.mvp.userinfo.UserInfoView;
import com.hamitao.zhiwan.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserInfoActivity extends AppCompatActivity implements UserInfoView {


    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666; // 从相册中选择图片进行扫码


    private AlertView mAlertViewExt;//窗口拓展例子
    private EditText etName;//拓展View内容
    private InputMethodManager imm;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_face)
    RelativeLayout rlFace;
    @BindView(R.id.rl_bbName)
    RelativeLayout rlBbName;
    @BindView(R.id.rb_boy)
    RadioButton rbBoy;
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;
    @BindView(R.id.rl_bbBirth)
    RelativeLayout rlbbBirth;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.iv_face)
    CircleImageView iv_face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onMessageShow(String msg) {

    }

    @Override
    public void getUserInfo(UserModel userModel) {

    }

    @Override
    public void updateSuccess(UserModel user) {

    }

    @Override
    public void updateFail() {

    }

    @OnClick({R.id.back, R.id.rl_face, R.id.rl_bbName, R.id.rl_bbBirth, R.id.rl_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.rl_face:
                new AlertView.Builder().setContext(this)
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("选择操作")
                        .setMessage(null)
                        .setCancelText("取消")
                        .setDestructive("拍照", "从相册中选择")
                        .setOthers(null)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                switch (position) {
                                    case 0:
                                        break;
                                    case 1:
                                        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(UserInfoActivity.this)
                                                .cameraFileDir(null)
                                                .maxChooseCount(1)
                                                .selectedPhotos(null)
                                                .pauseOnScroll(false)
                                                .build();
                                        startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                                        break;
                                }
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.rl_bbName:
                mAlertViewExt = new AlertView("提示", "请输入宝宝的名字", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {

                    }
                });
                ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form, null);
                etName = (EditText) extView.findViewById(R.id.et_input);
                etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean focus) {
                        //输入框出来则往上移动
                        boolean isOpen = imm.isActive();
                        mAlertViewExt.setMarginBottom(isOpen && focus ? 120 : 0);
                        System.out.println(isOpen);
                    }
                });
                mAlertViewExt.addExtView(extView);
                mAlertViewExt.show();
                break;
            case R.id.rl_bbBirth:
                break;
            case R.id.rl_logout:
                new AlertView("提示", "确定要退出吗?", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {

                    }
                }).setCancelable(true)
                        .setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(Object o) {

                            }
                        }).show();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
            //将图片路径设为头像
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            LogUtil.e("size", bitmap.getByteCount() + "");
            iv_face.setImageBitmap(bitmap);

        }
    }

}
