package ligang.huse.cn.googleplay.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import ligang.huse.cn.googleplay.global.GooglePalyAppclication;

/**
 * 工具类
 */
public class UiUitls {
    //获取常用工具方法
    public static Context getContex() {
        return GooglePalyAppclication.getContext();
    }

    public static Handler getHandle() {
        return GooglePalyAppclication.getHandler();
    }

    public static int getMainThread() {
        return GooglePalyAppclication.getMyTid();
    }

    //获取字符串
    public static String getString(int id) {
        return getContex().getResources().getString(id);
    }

    //获取字符串数组
    public static String[] getStringArray(int id) {
        return getContex().getResources().getStringArray(id);
    }

    //获取图片资源
    public static Drawable getDrawable(int id) {
        return getContex().getResources().getDrawable(id);
    }

    //获取颜色资源
    public static int getColor(int id) {
        return getContex().getResources().getColor(id);
    }

    //获取尺寸资源
    public static int getDimen(int id) {
        return getContex().getResources().getDimensionPixelSize(id);
    }


    //dp转px
    public static int dipTopx(float dip) {
        float density = getContex().getResources().getDisplayMetrics().density;
        return (int) (density * dip + 0.5);
    }

    //px转dp
    public static float pxTodip(int px) {
        float density = getContex().getResources().getDisplayMetrics().density;
        return px / density;
    }

    //加载布局文件
    public static View inflat(int id) {
        View view = View.inflate(getContex(), id, null);
        return view;
    }

    //判断是否在主线程
    public static boolean isRunOnUiThread() {
        int myTid = Process.myTid();
        if (myTid == getMainThread()) {
            return true;
        } else {
            return false;
        }
    }

    //将线程运行在UI线程中
    public static void runUiThread(Runnable r) {

        if (isRunOnUiThread()) {
            r.run();
        } else {
            getHandle().post(r);
        }

    }


    public static ColorStateList getColorStateList(int Id) {
       return getContex().getResources().getColorStateList(Id);
    }
}
