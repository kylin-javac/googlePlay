package ligang.huse.cn.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 *
 */
public class GooglePalyAppclication extends Application {

    private Context mContext;
    private Handler mHandler;
    private int mMyTid;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mMyTid = Process.myTid();//返回当前线程的id
    }

    public Context getContext() {
        return mContext;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public int getMyTid() {
        return mMyTid;
    }
}
