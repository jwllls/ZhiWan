package com.hamitao.zhiwan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.base_module.util.LogUtil;
import com.zhiwan.hamitao.base_module.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    private static final String TAG = "ScanActivity";
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666; // 从相册中选择图片进行扫码

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.scanView)
    ZXingView scanView;

    //正则（判断是否为网址）
    String regex = "^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        scanView.startCamera();     //打开相机
        scanView.showScanRect();    //显示矩形扫描框
        scanView.startSpotDelay(500);//延迟500毫秒开始识别
    }

    @Override
    protected void onStop() {
        scanView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        scanView.onDestroy();
        super.onDestroy();

    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        title.setText("二维码识别");
        more.setVisibility(View.VISIBLE);
        more.setText("相册");
        scanView.setDelegate(this);
    }

    /**
     * 震动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @OnClick({R.id.back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                //从相册中选取
                Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                        .cameraFileDir(null)
                        .maxChooseCount(1)
                        .selectedPhotos(null)
                        .pauseOnScroll(false)
                        .build();

                startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        scanView.showScanRect();

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
            /*
            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
//            new AsyncTask<Void, Void, String>() {
//                @Override
//                protected String doInBackground(Void... params) {
//                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
//                }
//
//                @Override
//                protected void onPostExecute(String result) {
//                    if (TextUtils.isEmpty(result)) {
//                        Toast.makeText(ScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }.execute();

          /*  Observable.just(QRCodeDecoder.syncDecodeQRCode(picturePath))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Toast.makeText(ScanActivity.this, "onSubscribe", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(String result) {
                            if (TextUtils.isEmpty(result)) {
                                Toast.makeText(ScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(ScanActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(ScanActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
                        }
                    });*/

        }
    }

    /**
     * 扫描成功
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {

        LogUtil.d(TAG, result);

        if (result.matches(regex)) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("result", result);
            startActivity(intent);
        } else {
            ToastUtil.showShort(this, result);
        }
        vibrate();  //震动
        scanView.startSpot(); //延迟1.5秒后继续扫描识别
    }

    /**
     * 扫描失败
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


}
