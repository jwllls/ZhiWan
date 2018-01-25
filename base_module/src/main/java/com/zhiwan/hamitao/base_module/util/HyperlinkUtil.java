package com.zhiwan.hamitao.base_module.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.zhiwan.hamitao.base_module.base.BaseActivity;
import com.zhiwan.hamitao.im_module.chat.utils.StringUtil;

import java.util.List;


/**
 * Created by Zenfer on 2017/6/8.
 * 超链接文本工具类
 */
public class HyperlinkUtil {

    private static final String match = "{@}";//超文本中的匹配字段

    /**
     * 获取超文本
     *
     * @param activity 不解释
     * @param content  文本
     * @param models   超链接数组
     * @return
     */
    public static SpannableStringBuilder getRichText(BaseActivity activity, String content, List<HyperlinkModel> models) {
        if (StringUtil.isBlank(content)) {
            //没有文本
            return new SpannableStringBuilder("");
        }
        if (models.size() <= 0) {
            //没有超链接
            return new SpannableStringBuilder(content);
        }
        int i = 0;
        SpannableStringBuilder text = new SpannableStringBuilder(content);
        while (text.toString().contains(match)) {
            if (i < models.size()) {
                HyperlinkModel model = models.get(i);
                //生成超链接
                SpannableStringBuilder sub3 = new SpannableStringBuilder(model.getTitle());
                sub3.setSpan(onClick(model, activity), 0, sub3.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                //替换文本中匹配到的字段
                text = text.replace(text.toString().indexOf(match),
                        text.toString().indexOf(match) + match.length(), sub3);
                i += 1;
            }
        }
        return text;
    }

    public static SpannableStringBuilder getNormalText(Activity activity, String content, List<HyperlinkModel> models) {
        if (StringUtil.isBlank(content)) {
            //没有文本
            return new SpannableStringBuilder("");
        }
        if (models == null || models.size() <= 0) {
            //没有超链接
            return new SpannableStringBuilder(content);
        }
        int i = 0;
        SpannableStringBuilder text = new SpannableStringBuilder(content);
        while (text.toString().contains(match)) {
            if (i < models.size()) {
                HyperlinkModel model = models.get(i);
                //生成超链接
                SpannableStringBuilder sub3 = new SpannableStringBuilder(model.getTitle());
                sub3.setSpan(onClick(model, activity), 0, sub3.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                //替换文本中匹配到的字段
                text = text.replace(text.toString().indexOf(match),
                        text.toString().indexOf(match) + match.length(), sub3);
                i += 1;
            }
        }
        return text;
    }

    /**
     * 超链接点击事件
     *
     * @param model    超链接数据
     * @param activity activity
     * @return ClickableSpan
     */
    public static ClickableSpan onClick(final HyperlinkModel model, final Activity activity) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (model != null) {
                    HyperlinkEnum hyperlinkEnum = HyperlinkEnum.getType(model.getTargetType());
                    if (hyperlinkEnum != null)
                        switch (hyperlinkEnum) {
                            case OUTER_WEB_VIEW:
                                //外部网页
                                Intent go_Intent = new Intent();
                                go_Intent.setAction("android.intent.action.VIEW");
                                Uri conUri = Uri.parse(model.getUrl());
                                go_Intent.setData(conUri);
                                activity.startActivity(go_Intent);
                                break;
                            case INNER_WEB_VIEW:
                                //内部网页
//                                Router.build("web").with("title", model.getTitle()).with("url", model.getUrl()).go(activity);
                                break;
                            case INFO:
                                //编辑资料页
//                                Router.build("person_info_edit").go(activity);
                                break;
                            case USER_INFO:
                                //用户资料
//                                Router.build("perfect_info").go(activity);
                                break;
                        }
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //#09ce99
                ds.setColor(Color.parseColor("#00baff"));
                ds.setUnderlineText(false);
            }
        };
    }


    /**
     * Created by Zenfer on 2017/6/8.
     * 超链接的model
     */
    public class HyperlinkModel {

        /**
         * {@link  HyperlinkEnum}
         */
        private int targetType;
        /**
         * 超链接标题
         */
        private String title;
        /**
         * 超文本链接
         */
        private String url;
        /**
         * 超链接参数
         */
        private String data;

        public int getTargetType() {
            return targetType;
        }

        public void setTargetType(int targetType) {
            this.targetType = targetType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    /**
     * 应用跳转类型枚举
     *
     * @author Zenfer
     */
    public enum HyperlinkEnum {
        INNER_WEB_VIEW(1, "内部浏览器跳转"),
        OUTER_WEB_VIEW(2, "外部浏览器跳转"),
        AUTH_VIDEO(3, "视频认证界面"),
        AUTH_SESAME(4, "芝麻认证界面"),
        INFO(5, "资料界面"),
        INVITE_SETTING(6, "邀约设置"),
        USER_INFO(7, "个人页面"),
        MY_WALLET(8, "我的钱包"),
        INVITE_ROUTE(9, "邀约行程"),
        SAFE_TO_HOME(10, "安全到家"),
        SECURITY_GUIDE(11, "安全码引导"),;

        private HyperlinkEnum(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        private int type;
        private String desc;

        public static HyperlinkEnum getType(int key) {
            HyperlinkEnum[] types = HyperlinkEnum.values();
            for (HyperlinkEnum type : types) {
                if (key == type.type) {
                    return type;
                }
            }
            return null;
        }

        public static boolean valid(int key) {
            HyperlinkEnum[] types = HyperlinkEnum.values();
            for (HyperlinkEnum type : types) {
                if (key == type.type) {
                    return true;
                }
            }
            return false;
        }

        public int getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }
}
