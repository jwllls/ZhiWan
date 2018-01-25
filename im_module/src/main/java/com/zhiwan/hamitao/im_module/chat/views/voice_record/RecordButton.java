package com.zhiwan.hamitao.im_module.chat.views.voice_record;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youyu.chat.R;


public class RecordButton extends AppCompatButton {

    private static final int MIN_RECORD_TIME = 1; // 最短录音时间，单位秒
    private static final int MAX_RECORD_TIME = 60; // 最长录音时间，单位秒
    private static final int RECORD_OFF = 0; // 不在录音
    private static final int RECORD_ON = 1; // 正在录音

    private Dialog mRecordDialog;
    private RecordStrategy mAudioRecorder;
    private Thread mRecordThread;
    private RecordListener listener;

    private int recordState = 0; // 录音状态
    private float recodeTime = 0.0f; // 录音时长，如果录音时间太短则录音失败
    private boolean isCanceled = false; // 是否取消录音
    private float downY;

    private TextView dialogTextView;
    private ImageView dialogImg;
    private ImageView dialogImgAmplitude;
    private Context mContext;

    private int BASE = 600;

    public RecordButton(Context context) {
        super(context);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.setText("按住说话");
        this.setTextColor(Color.parseColor("#bababa"));
        this.setBackgroundResource(R.drawable.chat_icon_bg_chat_input_n);
    }

    public void setAudioRecord(RecordStrategy record) {
        this.mAudioRecorder = record;
    }

    public void setRecordListener(RecordListener listener) {
        this.listener = listener;
    }

    // 录音时显示Dialog
    private void showVoiceDialog(int flag) {
        if (mRecordDialog == null) {
            mRecordDialog = new Dialog(mContext, R.style.DialogStyle);
            mRecordDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mRecordDialog.setContentView(R.layout.chat_dialog_record_toast);
            mRecordDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//去掉这句话，背景会变暗
            mRecordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogImg = (ImageView) mRecordDialog.findViewById(R.id.record_dialog_img);
            dialogImgAmplitude = (ImageView) mRecordDialog.findViewById(R.id.iv_record_amplitude);
            dialogTextView = (TextView) mRecordDialog.findViewById(R.id.record_dialog_txt);
        }
        switch (flag) {
            case 1:
                dialogImg.setImageResource(R.drawable.chat_icon_record_toast_cancel);
                dialogTextView.setText("松开手指 取消发送");
                dialogImgAmplitude.setVisibility(View.GONE);
                this.setText("松开取消");
                this.setTextColor(Color.parseColor("#828282"));
                this.setBackgroundResource(R.drawable.chat_icon_bg_chat_input_p);
                break;
            default:
                dialogImg.setImageResource(R.drawable.chat_icon_record_toast_mike);
                dialogTextView.setText("手指上滑 取消发送");
                dialogImgAmplitude.setVisibility(View.VISIBLE);
                this.setText("松开结束");
                this.setTextColor(Color.parseColor("#828282"));
                this.setBackgroundResource(R.drawable.chat_icon_bg_chat_input_p);
                break;
        }
        mRecordDialog.show();
    }

    // 录音时间太短时Toast显示
    private Toast mToast = null;
    private ImageView imageView;
    private TextView mTv;

    public void showWarnToast(String tip) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = new Toast(mContext);
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);

        imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.chat_icon_record_voice_to_short);

        mTv = new TextView(mContext);
        mTv.setText(tip);
        mTv.setGravity(Gravity.CENTER);
        mTv.setTextSize(14);
        mTv.setTextColor(Color.WHITE);

        linearLayout.addView(imageView);
        linearLayout.addView(mTv);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundResource(R.drawable.chat_shape_default_black_alpha);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(linearLayout);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    // 开启录音计时线程
    private void callRecordTimeThread() {
        mRecordThread = new Thread(recordThread);
        mRecordThread.start();
    }

    // 录音线程
    private Runnable recordThread = new Runnable() {

        @Override
        public void run() {
            recodeTime = 0.0f;
            while (recordState == RECORD_ON) {
                try {
                    Thread.sleep(100);
                    recodeTime += 0.1;
                    // 获取音量，更新dialog
                    double amplitude = mAudioRecorder.getAmplitude();
                    updateMicStatus(amplitude);
                    if (recodeTime >= MAX_RECORD_TIME) {
                        recordState = RECORD_OFF;
                        if (mRecordDialog.isShowing()) {
                            mRecordDialog.dismiss();
                        }
                        mAudioRecorder.stop();
                        mRecordThread.interrupt();
                        if (isCanceled) {
                            mAudioRecorder.deleteOldFile();
                        } else {
                            if (recodeTime < MIN_RECORD_TIME) {
                                showWarnToast("录音时间太短");
                                mAudioRecorder.deleteOldFile();
                            } else {
                                if (listener != null) {
                                    listener.recordEnd(mAudioRecorder.getFilePath(), (long) (recodeTime * 1000));
                                }
                            }
                        }
                        isCanceled = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //有录音权限
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下按钮
                if (CheckAudioPermission.isHasPermission(getContext())) {
                    if (recordState != RECORD_ON) {
                        showVoiceDialog(0);
                        downY = event.getY();
                        if (mAudioRecorder != null) {
                            mAudioRecorder.ready();
                            recordState = RECORD_ON;
                            mAudioRecorder.start();
                            callRecordTimeThread();
                        }
                    }
                } else {
                    //没有录音权限
                    Toast.makeText(getContext(), "请在设置中打开你的录音权限!", Toast.LENGTH_SHORT).show();
                    recordState = RECORD_OFF;
                }
                break;
            case MotionEvent.ACTION_MOVE: // 滑动手指
                if (recordState == RECORD_ON) {
                    float moveY = event.getY();
                    if (downY - moveY > 50) {
                        isCanceled = true;
                        showVoiceDialog(1);
                    }
                    if (downY - moveY < 20) {
                        isCanceled = false;
                        showVoiceDialog(0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP: // 松开手指
                if (recordState == RECORD_ON) {
                    recordState = RECORD_OFF;
                    if (mRecordDialog.isShowing()) {
                        mRecordDialog.dismiss();
                    }
                    mAudioRecorder.stop();
                    mRecordThread.interrupt();
                    if (isCanceled) {
                        mAudioRecorder.deleteOldFile();
                    } else {
                        if (recodeTime < MIN_RECORD_TIME) {
                            showWarnToast("录音时间太短");
                            mAudioRecorder.deleteOldFile();
                        } else {
                            if (listener != null) {
                                listener.recordEnd(mAudioRecorder.getFilePath(), (long) (recodeTime * 1000));
                            }
                        }
                    }
                    isCanceled = false;
                    this.setText("按住说话");
                    this.setBackgroundResource(R.drawable.chat_icon_bg_chat_input_n);
                }
                break;
        }
        return true;
    }

    /**
     * 更新话筒状态 分贝是也就是相对响度 分贝的计算公式K=20lg(Vo/Vi) Vo当前振幅值 Vi基准值为600：我是怎么制定基准值的呢？
     * 当20 * Math.log10(mMediaRecorder.getMaxAmplitude() / Vi)==0的时候vi就是我所需要的基准值
     * 当我不对着麦克风说任何话的时候，测试获得的mMediaRecorder.getMaxAmplitude()值即为基准值。
     * Log.i("mic_", "麦克风的基准值：" + mMediaRecorder.getMaxAmplitude());前提时不对麦克风说任何话
     *
     * @param amplitude 基准值
     */
    private void updateMicStatus(final double amplitude) {
        post(new Runnable() {
            @Override
            public void run() {
                int db = 0;// 分贝
                int ratio = (int) (amplitude / BASE);
                if (ratio > 1)
                    db = (int) (20 * Math.log10(ratio));
                System.out.println("分贝值：" + db + "     " + Math.log10(ratio));
                switch (db / 4) {
                    case 0:
                        dialogImgAmplitude.setImageBitmap(null);
                        break;
                    case 1:
                    case 2:
                        dialogImgAmplitude.setImageResource(R.drawable.chat_icon_amplitude_1);
                        break;
                    case 3:
                        dialogImgAmplitude.setImageResource(R.drawable.chat_icon_amplitude_2);
                        break;
                    case 4:
                        dialogImgAmplitude.setImageResource(R.drawable.chat_icon_amplitude_3);
                        break;
                    case 5:
                        dialogImgAmplitude.setImageResource(R.drawable.chat_icon_amplitude_4);
                        break;
                    default:
                        dialogImgAmplitude.setImageResource(0);
                        break;
                }
            }
        });

    }

    public interface RecordListener {
        public void recordEnd(String filePath, long recordTime);
    }
}
