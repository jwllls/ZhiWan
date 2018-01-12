package com.hamitao.zhiwan.mvp.music;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.base.BasePresenter;
import com.hamitao.zhiwan.model.RecordFileModel;
import com.hamitao.zhiwan.util.DateUtil;
import com.hamitao.zhiwan.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by linjianwen on 2018/1/9.
 */

public class RecordPresenter implements BasePresenter {

    private RecordView recordView;

    private Context context;

    private List<RecordFileModel> list = new ArrayList<>();

    private MediaRecorder mediaRecorder;

    //录音开始时间与结束时间
    private long startTime, endTime;

    //录音日期
    private Date recordDate;

    //录音所保存的文件
    private File mAudioFile;

    //录音文件保存位置
    private String mFilePath = Constant.USER_RECORD_LOCAL;

    //是否正在录音
    private boolean isRecord = false;


    public RecordPresenter(RecordView recordView, Context context) {
        this.recordView = recordView;
        this.context = context;

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    /**
     * @description 开始录音
     */
    public void startRecord() {
        //复位
        recordView.reset();
        //创建MediaRecorder对象
        mediaRecorder = new MediaRecorder();
        //创建录音文件,.m4a为MPEG-4音频标准的文件的扩展名
        mAudioFile = new File(mFilePath + System.currentTimeMillis() + ".aac");
        //创建父文件夹
        mAudioFile.getParentFile().mkdirs();
        try {
            //创建文件
            mAudioFile.createNewFile();
            //配置mMediaRecorder相应参数
            //从麦克风采集声音数据
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置保存文件格式为AAC
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            //设置采样频率,44100是所有安卓设备都支持的频率,频率越高，音质越好，当然文件越大
            mediaRecorder.setAudioSamplingRate(44100);
            //设置声音数据编码格式,音频通用格式是AAC
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //设置编码频率
            mediaRecorder.setAudioEncodingBitRate(96000);
            //设置录音保存的文件
            mediaRecorder.setOutputFile(mAudioFile.getAbsolutePath());
            //开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
            //正在录音
            isRecord = true;
            //记录开始录音时间
            startTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showShort(context, "录音失败");
        }
    }


    /**
     * 结束录音操作
     */
    public void stopRecord() {
        if (mediaRecorder != null) {
            //复位
            recordView.reset();
            //停止录音
            mediaRecorder.stop();
            isRecord = false;
            //记录停止时间
            endTime = System.currentTimeMillis();
            //记录录音日期
            recordDate = DateUtil.getDate(String.valueOf(System.currentTimeMillis()));
            //释放了录音资源
            releaseRecorder();
        }
    }

    /**
     * 设置录音文件名
     */
    public void showReNameDialog() {
            String oldFileName = mAudioFile.getName();
            View view = LayoutInflater.from(context).inflate(R.layout.record_edittext, null);
            final EditText et_rename = view.findViewById(R.id.et_reanme);
            final TextView tv_clean = view.findViewById(R.id.tv_clean);
            if (mAudioFile.getName().endsWith(".aac")) {
                et_rename.setText(oldFileName.substring(0, oldFileName.length() - 4)); //消除.aac后缀
                et_rename.setSelection(oldFileName.length() - 4);//光标放到末尾
            }

            tv_clean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et_rename.setText("");
                }
            });

            new AlertDialog.Builder(context).setTitle("命名和保存").setView(view)
                    .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newFilePath = mFilePath + et_rename.getText().toString() + ".aac";
                            File renameFile = new File(newFilePath);
                            mAudioFile.renameTo(renameFile); //重命名文件
                            ToastUtil.showShort(context, "保存成功");

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAudioFile.delete();

//                ToastUtil.showShort(context, "删除成功");
                }
            }).show();
            recordView.reset();//复位

    }

    /**
     * 重新录音
     */
    public void reRecord() {
        if (isRecord && mediaRecorder != null) {
            stopRecord();
            if (mAudioFile.exists()) {
                mAudioFile.delete();
            }
            startRecord();
        } else {
            startRecord();
        }
    }


    /**
     * 获取录音列表
     */
    public void setRecordList(){

        File file = new File(mFilePath);  //record文件夹

        File fa[] = file.listFiles();   //将record文件夹中的文件换为数组

        for (int i = 0; i< fa.length ; i++){

            RecordFileModel model = new RecordFileModel();

            model.setRecordFile(fa[i]);


            model.setRecordDate(String.valueOf(DateUtil.getDate(String.valueOf(file.lastModified()))));


            list.add(model);
        }

        recordView.getRecordList(list);
    }


    /**
     * 获取录音时长
     */
    public String getRecordDuration() {
        return DateUtil.formatmmss(endTime - startTime);
    }

    /**
     * 获取录音文件路径
     */
    public String getRecordDirectory() {
        return (mAudioFile != null) ? mAudioFile.getPath() : null;
    }

    /**
     * 翻放录音相关资源
     */
    private void releaseRecorder() {
        if (null != mediaRecorder) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


}
