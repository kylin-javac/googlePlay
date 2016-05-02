package ligang.huse.cn.googleplay.manager;

import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.domain.DownloadInfo;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.utils.IOUtils;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/5/1.
 * <p>
 * 文  件 googlePlay
 * <p>
 * 描  述 下载管理器---观察者模式
 */
public class DownLoadManager {
    public static final int STATE_NONE = 0;// 下载未开始
    public static final int STATE_WAITING = 1;// 等待下载
    public static final int STATE_DOWNLOAD = 2;// 正在下载
    public static final int STATE_PAUSE = 3;// 下载暂停
    public static final int STATE_ERROR = 4;// 下载失败
    public static final int STATE_SUCCESS = 5;// 下载成功
    ArrayList<DownloadObserver> mObservers = new ArrayList<>();
    private static DownLoadManager downloadManager = new DownLoadManager();
    ConcurrentHashMap<String, DownloadInfo> mDownloadInfoMap = new ConcurrentHashMap<String, DownloadInfo>();
    ConcurrentHashMap<String, DownloadTask> mDownloadTaskMap = new ConcurrentHashMap<String, DownloadTask>();

    private DownLoadManager() {
    }

    public static DownLoadManager getInstance() {
        return downloadManager;
    }


    // 开始下载
    public synchronized void download(AppInfo info) {
        // 如果对象是第一次下载, 需要创建一个新的DownloadInfo对象,从头下载
        // 如果之前下载过, 要接着下载,实现断点续传
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.getId());
        if (downloadInfo == null) {
            downloadInfo = DownloadInfo.copy(info);// 生成一个下载的对象
        }

        downloadInfo.currentState = STATE_WAITING;// 状态切换为等待下载
        notifyDownloadStateChanged(downloadInfo);// 通知所有的观察者, 状态发生变化了

        System.out.println(downloadInfo.name + "等待下载啦");

        // 将下载对象放入集合中
        mDownloadInfoMap.put(downloadInfo.id, downloadInfo);

        // 初始化下载任务, 并放入线程池中运行
        DownloadTask task = new DownloadTask(downloadInfo);
        ThreadManager.getInstance().execute(task);

        // 将下载任务放入集合中
        mDownloadTaskMap.put(downloadInfo.id, task);
    }


    // 下载暂停
    public synchronized void pause(AppInfo info) {
        // 取出下载对象
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.getId());

        if (downloadInfo != null) {
            // 只有在正在下载和等待下载时才需要暂停
            if (downloadInfo.currentState == STATE_DOWNLOAD
                    || downloadInfo.currentState == STATE_WAITING) {

                DownloadTask task = mDownloadTaskMap.get(downloadInfo.id);

                if (task != null) {
                    // 移除下载任务, 如果任务还没开始,正在等待, 可以通过此方法移除
                    // 如果任务已经开始运行, 需要在run方法里面进行中断
                    ThreadManager.getInstance().cancel(task);
                }

                // 将下载状态切换为暂停
                downloadInfo.currentState = STATE_PAUSE;
                notifyDownloadStateChanged(downloadInfo);
            }
        }
    }

    // 开始安装
    public synchronized void install(AppInfo info) {
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.getId());
        if (downloadInfo != null) {
            // 跳到系统的安装页面进行安装
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + downloadInfo.path),
                    "application/vnd.android.package-archive");
            UiUitls.getContex().startActivity(intent);
        }
    }

    // 下载任务对象
    class DownloadTask implements Runnable {

        private DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            System.out.println(downloadInfo.name + "开始下载啦");

            // 状态切换为正在下载
            downloadInfo.currentState = STATE_DOWNLOAD;
            notifyDownloadStateChanged(downloadInfo);

            File file = new File(downloadInfo.path);

            HttpHelper.HttpResult httpResult;

            if (!file.exists() || file.length() != downloadInfo.currentpos
                    || downloadInfo.currentpos == 0) {
                // 从头开始下载
                // 删除无效文件
                file.delete();// 文件如果不存在也是可以删除的, 只不过没有效果而已
                downloadInfo.currentpos = 0;// 当前下载位置置为0

                // 从头开始下载
                httpResult = HttpHelper.download(HttpHelper.URL
                        + "download?name=" + downloadInfo.downloadUrl);
            } else {
                // 断点续传
                // range 表示请求服务器从文件的哪个位置开始返回数据
                httpResult = HttpHelper.download(HttpHelper.URL
                        + "download?name=" + downloadInfo.downloadUrl
                        + "&range=" + file.length());
            }

            if (httpResult != null && httpResult.getInputStream() != null) {

                InputStream in = httpResult.getInputStream();
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file, true);// 要在原有文件基础上追加数据

                    int len = 0;
                    byte[] buffer = new byte[1024];

                    // 只有状态是正在下载, 才继续轮询. 解决下载过程中中途暂停的问题
                    while ((len = in.read(buffer)) != -1
                            && downloadInfo.currentState == STATE_DOWNLOAD) {
                        out.write(buffer, 0, len);
                        out.flush();// 把剩余数据刷入本地

                        // 更新下载进度
                        downloadInfo.currentpos += len;
                        notifyDownloadProgressChanged(downloadInfo);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(in);
                    IOUtils.close(out);
                }

                // 文件下载结束
                if (file.length() == downloadInfo.size) {
                    // 文件完整, 表示下载成功
                    downloadInfo.currentState = STATE_SUCCESS;
                    notifyDownloadStateChanged(downloadInfo);
                } else if (downloadInfo.currentState == STATE_PAUSE) {
                    // 中途暂停
                    notifyDownloadStateChanged(downloadInfo);
                } else {
                    // 下载失败
                    file.delete();// 删除无效文件
                    downloadInfo.currentState = STATE_ERROR;
                    downloadInfo.currentpos = 0;
                    notifyDownloadStateChanged(downloadInfo);
                }
            } else {
                // 网络异常
                file.delete();// 删除无效文件
                downloadInfo.currentState = STATE_ERROR;
                downloadInfo.currentpos = 0;
                notifyDownloadStateChanged(downloadInfo);
            }

            // 从集合中移除下载任务
            mDownloadTaskMap.remove(downloadInfo.id);
        }

    }

    /**
     * 1. 定义下载观察者接口
     */
    public interface DownloadObserver {
        //下载变化
        public void OnDownloadStateChanged(DownloadInfo info);

        //下载进度
        public void OnDownloadProgressChanged(DownloadInfo info);
    }

    // 2. 注册观察者
    public void registerObserver(DownloadObserver observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    // 3. 注销观察者
    public void unregisterObserver(DownloadObserver observer) {
        if (observer != null && mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    // 4. 通知下载状态变化
    public void notifyDownloadStateChanged(DownloadInfo info) {
        for (DownloadObserver observer : mObservers) {
            observer.OnDownloadStateChanged(info);
        }
    }

    // 5. 通知下载进度变化
    public void notifyDownloadProgressChanged(DownloadInfo info) {
        for (DownloadObserver observer : mObservers) {
            observer.OnDownloadProgressChanged(info);
        }
    }
    // 根据应用信息返回下载对象
    public DownloadInfo getDownloadInfo(AppInfo info) {
        return mDownloadInfoMap.get(info.getId());
    }

}
