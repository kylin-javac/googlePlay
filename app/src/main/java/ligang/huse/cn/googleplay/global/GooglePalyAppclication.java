package ligang.huse.cn.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 *
 */
public class GooglePalyAppclication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mMyTid;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mMyTid = Process.myTid();//返回当前线程的id
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMyTid() {
        return mMyTid;
    }
}
