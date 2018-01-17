package com.hamitao.zhiwan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.dialog.ActionSheetDialog;
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
    @BindView(R.id.rl_bbBitrh)
    RelativeLayout rlBbBitrh;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    @BindView(R.id.iv_face)
    CircleImageView iv_face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
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

    @OnClick({R.id.back, R.id.rl_face, R.id.rl_bbName, R.id.rl_bbBitrh, R.id.rl_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.rl_face:
                new ActionSheetDialog(this).builder().addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
//                        PictureSelectorUtil.showPictureSelector(PhotoAvartaActivity.this, 1, 2, 1, onSelectResultCallback);
                    }
                }).addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
//                        PictureSelectorUtil.showPictureSelector(PhotoAvartaActivity.this, 2, 2, 1, onSelectResultCallback);

                        //从相册中选取
                        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(UserInfoActivity.this)
                                .cameraFileDir(null)
                                .maxChooseCount(1)
                                .selectedPhotos(null)
                                .pauseOnScroll(false)
                                .build();

                        startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                    }
                }).show();
                break;
            case R.id.rl_bbName:
                break;
            case R.id.rl_bbBitrh:
                break;
            case R.id.rl_logout:
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
            LogUtil.e("size",bitmap.getByteCount()+"");
            iv_face.setImageBitmap(bitmap);

        }
    }
}
