package com.lzy.basemodule.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 创建者   Administrator
 * 创建时间 2018/7/7 17:18
 * 描述:   TODO
 */

public class TextUtil {

    /**
     * 关键字高亮显示
     *
     * @param context 上下文
     * @param text    需要显示的文字
     * @param target  需要高亮的关键字
     * @param color   高亮颜色
     * @param start   头部增加高亮文字个数
     * @param end     尾部增加高亮文字个数
     * @return 处理完后的结果
     */
    public static SpannableString highlight(Context context, String text, String target, String color, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
            spannableString.setSpan(span, matcher.start() - start, matcher.end() + end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    //截取数字
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 截取非数字
    public String splitNotNumber(String content) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 判断一个字符串是否含有数字
    public boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    //收起输入法
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 将文本长度/2处理
     *
     * @param text
     * @return
     */
    public static String getTextHalf(String text) {
        if (!text.isEmpty()) {
            int Half = text.length() / 2;
            String substring = text.substring(0, Half);
            String replace = text.replace(substring, substring + "\n");
            return replace;
        } else {
            return "";
        }
    }


    /**
     * 获取明天时间 日期
     *
     * @return
     */
    public static String getTimeTomorrow(long time) throws ParseException {
        //获取单前时间日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(time);

        //将当前时间日期往后退一天
        Date parse = formatter.parse(format);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(parse);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        String format1 = formatter.format(calendar.getTime());
        return format1;
    }


//    /**
//     * @param time   动画时间
//     * @param repeat 重复次数
//     * @param view   控件
//     */
//    public static void ViewShakeAnimator(int time, int repeat, View view) {
//        YoYo.with(Techniques.Shake)
//                .duration(time)
//                .repeat(repeat)
//                .playOn(view);
//    }


}