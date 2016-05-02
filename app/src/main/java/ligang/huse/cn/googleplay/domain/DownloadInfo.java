package ligang.huse.cn.googleplay.domain;

import android.os.Environment;

import java.io.File;

import ligang.huse.cn.googleplay.manager.DownLoadManager;

/**
 * 创建时间 javac on 2016/5/1.
 * <p>
 * 文  件 googlePlay
 * <p>
 * 描  述 下载对象
 */
public class DownloadInfo {
    public static final String GOOGLE_MARKET = "GOOGLE_MARKET";
    public static final String DOWNLOAD = "download";

    public String id;
    public String name;
    public String downloadUrl;
    public long size;
    public String packageName;
    public long currentpos;//当前下载位置
    public int currentState;//当前下载状态
    public String path;//下载路径

    /**
     * 根据应用信息,拷贝出一个下载对象
     */


    public float getProgress() {//获取下载进度

        return currentpos / (float) size;
    }


    public static DownloadInfo copy(AppInfo appInfo) {
        DownloadInfo info = new DownloadInfo();
        info.id = appInfo.getId();
        info.size = appInfo.getSize();
        info.downloadUrl = appInfo.getDownloadUrl();
        info.name = appInfo.getName();
        info.currentState = DownLoadManager.STATE_NONE;
        info.currentpos = 0;
        info.path = info.getFilePath();

        return info;
    }

    public String getFilePath() {//获取文件的下载路径
        StringBuilder SdCard = new StringBuilder();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        SdCard.append(path);
        SdCard.append(File.separator);
        SdCard.append(GOOGLE_MARKET);
        SdCard.append(File.separator);
        SdCard.append(DOWNLOAD);
        if (createDir(SdCard.toString())) {
            return SdCard.toString() + File.separator + name + ".apk";
        }
        return null;
    }


    public boolean createDir(String Dir) {
        File file = new File(Dir);
        if (!file.exists() || !file.isDirectory()) {//只要文件不存在或者不是文件夹就要创建文件夹
            return file.mkdirs();
        }
        return true;
    }


}
