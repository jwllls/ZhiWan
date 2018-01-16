package com.hamitao.zhiwan.mvp.record;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hamitao.zhiwan.Constant;
import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.activity.RecordActivity;
import com.hamitao.zhiwan.base.BasePresenter;
import com.hamitao.zhiwan.model.RecordModel;
import com.hamitao.zhiwan.util.DateUtil;
import com.hamitao.zhiwan.util.FileUtil;
import com.hamitao.zhiwan.util.RecordUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linjianwen on 2018/1/9.
 */

public class RecordPresenter implements BasePresenter {

    private RecordView recordView;

    private Context context;

    private List<RecordModel> list = new ArrayList<>();

    //录音所保存的文件
    private File mAudioFile;

    //录音文件保存位置
    private String mFilePath;

    //是否正在录音
    private boolean isRecord = false;


    public RecordPresenter(RecordView recordView, Context context) {
        this.recordView = recordView;
        this.context = context;
        start();

    }


    /**
     * 初始化
     */
    @Override
    public void start() {
        mAudioFile = RecordUtil.getInstance().getRecordFile();
        mFilePath = RecordUtil.getInstance().getRecordDirtory();

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
        RecordUtil.getInstance().startRecord();
        isRecord = true;
        recordView.showButton();
    }


    /**
     * 结束录音操作
     */
    public void stopRecord() {
        RecordUtil.getInstance().stopRecord();
        isRecord = false;
    }

    /**
     * 设置录音文件名
     */
    public void showReNameDialog() {

        mAudioFile = RecordUtil.getInstance().getRecordFile();

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
                        recordView.onMessageShow("保存成功");
                        recordView.hideButton();//隐藏重录、保存按钮
                        recordView.reset();//复位
                        setRecordList();
                        ((RecordActivity)context).finish();

                    }
                }).setNegativeButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mAudioFile.delete();
                recordView.hideButton();//隐藏重录、保存按钮
                recordView.reset();//复位

            }
        }).show();


    }


    public void showRerecordDialog() {

        new AlertDialog.Builder(context).setTitle("提示").setMessage("是否重新录音？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recordView.reRecord();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消，不做操作
            }
        }).show();


    }


    /**
     * 重新录音
     */
    public void reRecord() {
        if (isRecord) {
            stopRecord();
            if (mAudioFile != null && mAudioFile.exists()) {
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
    public void setRecordList() {

        File file = new File(Constant.USER_RECORD_LOCAL);  //record文件夹

        File fa[] = file.listFiles();   //将record文件夹中的文件换为数组

        for (int i = fa.length-1; i >= 0 ; i--) {

            RecordModel model = new RecordModel();

            model.setRecordFile(fa[i]);

            model.setRecordDate(DateUtil.formatyyyyMMdd(fa[i].lastModified()));

            try {
                model.setFileSize(FileUtil.formatFileSize(FileUtil.getFileSize(fa[i])));
            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(model);
        }

        recordView.getRecordList(list);

    }

}
