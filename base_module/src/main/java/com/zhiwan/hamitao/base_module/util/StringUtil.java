package com.zhiwan.hamitao.base_module.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linjianwen on 2018/1/5.
 */

public class StringUtil {

    private static StringUtil STRING_UTIL = null;

    public static StringUtil getInstance() {
        // TODO Auto-generated method stub
        if (STRING_UTIL == null) {
            STRING_UTIL = new StringUtil();
        }
        return STRING_UTIL;
    }

    private static final long TIME_SECOND = 1 * 1000L;
    private static final long TIME_MINUTE = 60 * TIME_SECOND;
    private static final long TIME_HOUR = 60 * TIME_MINUTE;
    private static final long TIME_DAY = 24 * TIME_HOUR;
    private static final long TIME_WEEK = 7 * TIME_DAY;
    private static final long TIME_MONTH = 4 * TIME_WEEK;

    private static final String BIAOQING_REGEX = "\\[em\\d=\\d+\\]";
    private static final String BIAOQING_STR_REGEX = "^.*" + BIAOQING_REGEX
            + "$";
    private static final Pattern BIAOQING_PATTERN = Pattern
            .compile("\\[em\\d=\\d+\\]");

    private static final String REPLY_REGEX = "^回复(\\d+)楼：.*$";
    private static final Pattern REPLY_PATTERN = Pattern.compile(REPLY_REGEX);

    private static final String HTML_REGEX = "[\\s\\S]*&#(\\d+?);[\\s\\S]*";
    private static final String HTML_MATCHER_REGEX = "&#(\\d+?);";
    private static final Pattern HTML_PATTERN = Pattern
            .compile(HTML_MATCHER_REGEX);

    private static final SimpleDateFormat HmFormat = new SimpleDateFormat("H:m");
    private static final SimpleDateFormat weekFormat = new SimpleDateFormat("E");
    private static final SimpleDateFormat MdHmFormat = new SimpleDateFormat("M-d H:m");

    private static final SimpleDateFormat YYYYMMDDFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean isBlank(String src) {
        return null == src || "".equals(src.trim());
    }

    public static boolean isNotBlank(String src) {
        return null != src && (!"".equals(src.trim()));
    }

    /**
     * 字符串转成Html
     *
     * @param src
     * @return
     */
    public static String txcontent2Html(String src) {
        src = src.replaceAll("\r\n", "<br/>");
        src = src.replaceAll("\r", "<br/>");
        src = src.replaceAll("\n", "<br/>");
        return src;
    }

    /**
     * 把字符串转成long型
     *
     * @param src
     * @return
     */
    public static long str2Long(String src) {
        return src.trim().matches("^\\d+$") ? new Long(src.trim()) : 0L;
    }

    public static String stringFilter(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //unicode为12288的字符显示为空格，但是用Java trim()函数无法去除。
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //char转unsigned char 之中是不会丢失符号位的，符号位保留，当做一个位来看
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String getRandomStr() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 使用java正则表达式判断str是否为 大于等于0的整数
     *
     * @param str
     * @return
     */
    public static boolean isPositiveInteger(String str) {
        return str.matches("[0-9]+?");
    }

    /**
     * 如果是当分,则N秒前 如果是当前小时,则 N分钟前 如果是当日,则 H:m 如果是昨天,则 昨天 H:m 如果是一周内,则 星期 H:m
     * 如果超过一周,则 M-d H:m
     *
     * @param time
     * @return
     */
    public static String getFormatDate(Long time) {
        if (null == time) {
            return "刚刚";
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - time < TIME_MINUTE) {
            long dataTime = (currentTime - time) / TIME_SECOND + 1;
            if (dataTime < 0) {
                return "刚刚";
            } else {
                return dataTime + "秒前";
            }

        }
        if (currentTime - time < TIME_HOUR) {
            return (currentTime - time) / TIME_MINUTE + "分钟前";
        }
        if (time > ((currentTime / TIME_DAY) * TIME_DAY)) {
            return HmFormat.format(new Date(time));
        }
        if (time > ((currentTime / TIME_DAY - 1) * TIME_DAY)) {
            return "昨天 " + HmFormat.format(new Date(time));
        }
        Calendar pre = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        pre.setTimeInMillis(time);
        now.setTimeInMillis(currentTime);
        if (isSameWeekDates(pre, now)) {
            return weekFormat.format(pre.getTime());
        }
        return MdHmFormat.format(pre.getTime());
    }

    /**
     * 判断是否在线
     *
     * @param time
     * @return
     */
    public static boolean isOnline(Long time) {
        long currentTime = System.currentTimeMillis();
        return (currentTime - time < TIME_HOUR * 48);
    }


    /**
     * 两个时间是否为同一周
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameWeekDates(Calendar cal1, Calendar cal2) {
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    private static String getTextViewImageStr(int resId) {
        return "<img src=\"" + resId + "\">";
    }

    /***
     * 网络地址 转换为本地所需要存放的地址
     *
     * @param url
     * @return
     */
    public static String getPicLocalUrl(String url) {
        if (isBlank(url)) {
            return "";
        }
        return url.replaceAll("http://", "").replaceAll("/", "")
                .replaceAll("\\.", "");
    }


    public static Byte getByteValue(String value) {
        if (StringUtil.isBlank(value)) {
            return null;
        }
        try {
            return Byte.parseByte(value.trim());
        } catch (Throwable e) {
            return null;
        }
    }

    public static Integer getIntValue(String value) {
        if (StringUtil.isBlank(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (Throwable e) {
            return null;
        }
    }

    public static Long getLongValue(String value) {
        if (StringUtil.isBlank(value)) {
            return null;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 获取当前的栈
     *
     * @return
     */
    public static String getCurrentStack() {
        StackTraceElement[] stacks = new Exception().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stack : stacks) {
            sb.append(stack.getClassName() + "." + stack.getMethodName() + "\n");
        }
        return sb.toString();
    }

    /**
     * 从路径中获取文件名字
     *
     * @param uri
     * @return
     */
    public static String getFileNameFromUri(String uri) {
        if (StringUtil.isBlank(uri)) {
            return null;
        }
        return uri.substring(uri.lastIndexOf("/") + 1, uri.length());
    }


    /**
     * 格式化HTML文本
     *
     * @param content
     * @return
     */
    public static String deHtml(String content) {
        if (StringUtil.isBlank(content)) {
            return content;
        }
        content = content.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">").replaceAll("&apos;", "\'")
                .replaceAll("&quot;", "\"").replaceAll("&nbsp;", " ")
                .replaceAll("&copy;", "@").replaceAll("&reg;", "?")
                .replaceAll("&ensp;", " ").replaceAll("&emsp;", " ")
                .replaceAll("&times;", "×").replaceAll("&divide;", "÷");
        if (!content.matches(HTML_REGEX)) {
            return content;
        }
        Matcher matcher = HTML_PATTERN.matcher(content);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, Character.toString((char) Integer
                    .parseInt(matcher.group(1))));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * 两个字符串是否一样
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isEqual(Object str1, Object str2) {
        if (str1 == null || str2 == null) {
            return str1 == str2;
        }
        return str1.equals(str2);
    }

    /**
     * 字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public static class ReplyAns {
        public int floor;
        public String content;
    }


    public static ReplyAns getReplyAnysics(String content) {
        ReplyAns ra = new ReplyAns();
        if (StringUtil.isBlank(content)) {
            return ra;
        }
        if (!content.matches(REPLY_REGEX)) {
            ra.floor = 0;
            ra.content = content;
            return ra;
        }
        Matcher matcher = REPLY_PATTERN.matcher(content);
        while (matcher.find()) {
            ra.floor = Integer.valueOf(matcher.group(1));
            if ((content.indexOf("：") + 1) == content.length()) {
                ra.content = null;
            } else {
                ra.content = content.substring(content.indexOf("：") + 1,
                        content.length());
            }
            return ra;
        }
        return ra;
    }

    public static Date simpleDateParse(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static int ignoreBlankLength(String str) {
        if (StringUtil.isBlank(str)) {
            return 0;
        }
        return str.replaceAll("[ 　]", "").length();
    }

    // 判断手机格式是否正确

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern
                .compile("^((13[0-9])|(15[0-9])|(18[0-9])|(16[0-9])|(14[0-9])|(17[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    public static boolean isPassWord(String pass) {
        // 允许空字符串就最后用*.不允许就+
        Pattern ptn = Pattern.compile("^[A-Za-z0-9]{6,18}+$");
        Matcher mth = ptn.matcher(pass);
        return mth.find();
    }


    /**
     * 把字符串转成bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 设置多彩字符串
     *
     * @param textView
     * @param head
     * @param span
     * @param bottom
     * @param color
     */
    public static void setSpanText(TextView textView, String head, String span, String bottom, int color) {
        if (StringUtil.isBlank(span)) return;
        textView.append(head);
        SpannableString ss = new SpannableString(span);
        ss.setSpan(new ForegroundColorSpan(color), 0, span.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(ss);
        textView.append(bottom);
    }

    /**
     * 设置多彩字符串
     *
     * @param textView
     * @param head
     * @param span
     * @param text
     * @param color
     */
    public static void setSpanText(TextView textView, String head, String span, CharSequence text, int color) {
        textView.append(head);
        SpannableString ss = new SpannableString(span);
        ss.setSpan(new ForegroundColorSpan(color), 0, span.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(ss);
        textView.append(text);
    }

    public static void setSpanTextHead(TextView textView, String head, String bottom, int color) {
        SpannableString ss = new SpannableString(head);
        ss.setSpan(new ForegroundColorSpan(color), 0, head.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(ss);
        textView.append(bottom);
    }

    /*********************** 字符串转码 开始 *************************/
    /**
     * 转换字符串的编码
     */
    /**
     * 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
     */
    public static final String US_ASCII = "US-ASCII";

    /**
     * ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 8 位 UCS 转换格式
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
     */
    public static final String UTF_16BE = "UTF-16BE";

    /**
     * 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
     */
    public static final String UTF_16LE = "UTF-16LE";

    /**
     * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
     */
    public static final String UTF_16 = "UTF-16";

    /**
     * 中文超大字符集
     */
    public static final String GBK = "GBK";

    /**
     * 将字符编码转换成US-ASCII码
     */
    public static String toASCII(String str)
            throws UnsupportedEncodingException {
        return changeCharset(str, US_ASCII);
    }

    /**
     * 将字符编码转换成ISO-8859-1码
     */
    public static String toISO_8859_1(String str)
            throws UnsupportedEncodingException {
        return changeCharset(str, ISO_8859_1);
    }

    /**
     * 将字符编码转换成UTF-8码
     */
    public static String toUTF_8(String str)
            throws UnsupportedEncodingException {
        return changeCharset(str, UTF_8);
    }

    /**
     * 将字符编码转换成UTF-16BE码
     */
    public static String toUTF_16BE(String str)
            throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16BE);
    }

    /**
     * 将字符编码转换成UTF-16LE码
     */
    public static String toUTF_16LE(String str)
            throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16LE);
    }

    /**
     * 将字符编码转换成UTF-16码
     */
    public static String toUTF_16(String str)
            throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16);
    }

    /**
     * 将字符编码转换成GBK码
     */
    public static String toGBK(String str) throws UnsupportedEncodingException {
        return changeCharset(str, GBK);
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String oldCharset,
                                       String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }
    /*********************** 字符串转码 结束 *************************/

    /**
     * 用取余运算
     */
    public static boolean isEven(int num) {
        if (num % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 过滤回车和空格
     *
     * @param content
     * @return
     */
    public static String contentFilter(String content) {
        //过滤回车
        Pattern CRLF = Pattern.compile("(\\\\r\\\\n|\\\\r|\\\\n|\\\\n\\\\r)");
        Matcher m = CRLF.matcher(content);
        if (m.find()) {
            content = m.replaceAll("");
        }
        return content;
    }
}
