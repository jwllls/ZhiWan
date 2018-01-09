package com.zhiwan.hamitao.zhiwan.mvp.music;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

import com.zhiwan.hamitao.zhiwan.Constant;
import com.zhiwan.hamitao.zhiwan.base.BasePresenter;
import com.zhiwan.hamitao.zhiwan.util.ToastUtil;

import java.io.File;

/**
 * Created by linjianwen on 2018/1/9.
 */

public class RecordPresenter implements BasePresenter {

    private RecordView recordView;
    private Context context;

    private MediaRecorder mediaRecorder;

    //录音开始时间与结束时间
    private long startTime, endTime;
    //录音所保存的文件
    private File mAudioFile;
    //录音文件保存位置
    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZhiWan/";

    private String mFilePath = Constant.USER_RECORD_LOCAL;


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
            //记录开始录音时间
            startTime = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showShort(context,"录音失败");
        }
    }


    /**
     * @description 结束录音操作
     * @author ldm
     * @time 2017/2/9 9:18
     */
    public void stopRecord() {
//        start_tv.setText(R.string.speak_by_press);
//        start_tv.setBackgroundResource(R.drawable.bg_white_round);
        //停止录音
        mediaRecorder.stop();
        //记录停止时间
        endTime = System.currentTimeMillis();
//        //录音时间处理，比如只有大于2秒的录音才算成功
//        int time = (int) ((endTime - startTime) / 1000);
//        if (time >= 3) {
//            //录音成功,添加数据
//            FileBean bean = new FileBean();
//            bean.setFile(mAudioFile);
//            bean.setFileLength(time);
//            dataList.add(bean);
//            //录音成功,发Message
//            mHandler.sendEmptyMessage(Constant.RECORD_SUCCESS);
//        } else {
//            mAudioFile = null;
//            mHandler.sendEmptyMessage(Constant.RECORD_TOO_SHORT);
//        }
        //录音完成释放资源
        releaseRecorder();
    }


    /**
     * @description 翻放录音相关资源
     * @author ldm
     * @time 2017/2/9 9:33
     */
    private void releaseRecorder() {
        if (null != mediaRecorder) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


}
