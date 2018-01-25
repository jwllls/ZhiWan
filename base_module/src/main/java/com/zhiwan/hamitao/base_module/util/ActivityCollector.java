package com.zhiwan.hamitao.base_module.util;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by linjianwen on 2018/1/15.
 */

public class ActivityCollector {


    private static final List<Activity> list = new ArrayList<>();

    public static void addActivity(Activity activity) {
        list.add(activity);
    }

    public static void removeActivity(Activity activity) {
        list.remove(activity);
    }

    public static void clearAll() {
        if (list.size() > 0) {
            Iterator<Activity> itor = list.iterator();
            while (itor.hasNext()) {
                Activity activity = itor.next();
                activity.finish();
                itor.remove();
            }
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity getCurrentActivity() {
        if (list.size() > 0)
            return list.get(list.size() - 1);
        else
            return null;
    }

    public static <T> void clearAllExcept(Class<T> clazz) {
        if (list.size() > 0) {
            Log.d("baseActivity", "before clear alive:" + list.size());
            Iterator<Activity> itor = list.iterator();
            while (itor.hasNext()) {
                Activity activity = itor.next();
                if (!clazz.isAssignableFrom(activity.getClass())) {
                    activity.finish();
                    itor.remove();
                }
            }
            Log.d("baseActivity", "after clear alive:" + list.size());
        }
    }

    public static <T> void clearActivity(Class<T> clazz) {
        if (list.size() > 0) {
            Activity activity = getActivity(clazz);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void clearAllExcept(Activity except) {
        if (list.size() > 0) {
            Log.d("baseActivity", "before clear alive:" + list.size());
            Iterator<Activity> itor = list.iterator();
            while (itor.hasNext()) {
                Activity activity = itor.next();
                if (activity != except) {
                    activity.finish();
                    itor.remove();
                }
            }
            Log.d("baseActivity", "after clear alive:" + list.size());
        }
    }

    public static <T> Activity getActivity(Class<T> clazz) {
        if (list.size() > 0) {
            Log.d("baseActivity", "before clear alive:" + list.size());
            Iterator<Activity> itor = list.iterator();
            while (itor.hasNext()) {
                Activity activity = itor.next();
                if (clazz.isAssignableFrom(activity.getClass())) {
                    return activity;
                }
            }
        }
        return null;
    }
}
