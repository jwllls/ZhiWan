package com.zhiwan.hamitao.zhiwan.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * Created by linjianwen on 2018/1/10.
 * File 处理工具类
 */

public class FileUtil {

    /**
     * 读取asset目录下文件
     *
     * @param context       上下文
     * @param assetFileName 文件名
     * @return
     */
    public static String readAssetFile(Context context, String assetFileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(assetFileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder builder = new StringBuilder();

        char[] buffer = new char[4 * 1024];
        int readLength;
        while (true) {
            try {
                readLength = reader.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return null;
            }
            if (readLength >= 0) {
                builder.append(buffer, 0, readLength);
            } else {
                break;
            }
        }
        try {
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return builder.toString();
    }

    /**
     * 判断SD是否可以
     *
     * @return
     */
    public static boolean isSdcardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 创建根目录
     *
     * @param path 目录路径
     */
    public static void createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹的路径
     */
    public static void delFolder(String folderPath) {
        delAllFile(folderPath);
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        myFilePath.delete();
    }

    /**
     * 删除文件
     *
     * @param path 文件的路径
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
            }
        }
    }

    /**
     * 获取文件的Uri
     *
     * @param path 文件的路径
     * @return
     */
    public static Uri getUriFromFile(String path) {
        File file = new File(path);
        return Uri.fromFile(file);
    }

    /**
     * 换算文件大小
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "未知大小";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 将 文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param srcPath 生成64编码的文件的路径
     * @return
     */
    public static String getFileStr(String srcPath) {
        InputStream in = null;
        byte[] data = null;

        //读取文件字节数组
        try {
            in = new FileInputStream(srcPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //对字节数组Base64编码
        return Base64.encodeToString(data, Base64.DEFAULT);//返回Base64编码过的字节数组字符串
    }
    /**
     * 通知媒体库更新文件
     * @param context
     * @param filePath 文件全路径
     *
     * */
    public static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    /**
     * 获取指定文件大小(单位：字节)
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        if (file == null) {
            return 0;
        }
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }


    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    public static void renameFile(String oldPath, String newPath) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }



}
