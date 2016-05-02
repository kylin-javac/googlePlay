package ligang.huse.cn.googleplay.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.domain.DownloadInfo;
import ligang.huse.cn.googleplay.manager.DownLoadManager;
import ligang.huse.cn.googleplay.ui.view.ProgressHorizontal;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/5/1.
 * <p>
 * 文  件 googlePlay
 * <p>
 * 描  述 详情页 下载模块
 */
public class DetailDownloadHolder extends BaseHolder<AppInfo> implements DownLoadManager.DownloadObserver, View.OnClickListener {
    private Button mBtnFav;
    private Button mBtnShare;
    private DownLoadManager mDm;
    private ProgressHorizontal pbProgress;
    private FrameLayout flDownload;
    private Button btnDownload;
    private AppInfo mAppInfo;
    private int mCurrentState;
    private float mProgress;


    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.layout_detail_download);
        mBtnFav = (Button) view.findViewById(R.id.btn_fav);
        mBtnShare = (Button) view.findViewById(R.id.btn_share);
        btnDownload = (Button) view.findViewById(R.id.btn_download);
        flDownload = (FrameLayout) view.findViewById(R.id.fl_progress);
        btnDownload.setOnClickListener(this);
        flDownload.setOnClickListener(this);
        // 添加进度条布局
        pbProgress = new ProgressHorizontal(UiUitls.getContex());
        pbProgress.setProgressTextColor(Color.WHITE);// 文字颜色为白色
        pbProgress.setProgressTextSize(UiUitls.dipTopx(18));// 文字大小
        pbProgress.setProgressResource(R.drawable.progress_normal);// 进度条图片
        pbProgress.setProgressBackgroundResource(R.drawable.progress_bg);// 进度条背景图片
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        flDownload.addView(pbProgress, params);
        mDm = DownLoadManager.getInstance();
        mDm.registerObserver(this);//注册观察者，监听状态和进度的变化
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        DownloadInfo downloadInfo = mDm.getDownloadInfo(data);
        if (downloadInfo != null) {
            //之前下载过
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        } else {
            //没有下载过
            mCurrentState = DownLoadManager.STATE_NONE;
            mProgress = 0;
        }
        refreshUI(mCurrentState, mProgress);

    }

    //根据当前的下载进度和状态更新界面
    private void refreshUI(int currentState, float progress) {
        mProgress = progress;
        mCurrentState = currentState;
        switch (currentState) {
            case DownLoadManager.STATE_NONE:// 未下载
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
                break;

            case DownLoadManager.STATE_WAITING:// 等待下载
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待中..");
                break;

            case DownLoadManager.STATE_DOWNLOAD:// 正在下载
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("");
                pbProgress.setProgress(mProgress);// 设置下载进度
                break;

            case DownLoadManager.STATE_PAUSE:// 下载暂停
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("暂停");
                pbProgress.setProgress(mProgress);

                System.out.println("暂停界面更新:" + mCurrentState);
                break;

            case DownLoadManager.STATE_ERROR:// 下载失败
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载失败");
                break;

            case DownLoadManager.STATE_SUCCESS:// 下载成功
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("安装");
                break;
            default:
                break;
        }


    }


    //在主界面更新UI
    private void refreshUIMainThread(final DownloadInfo info) {
        UiUitls.runUiThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(info.currentState, info.getProgress());
            }
        });

    }

    /**
     * 状态更新
     *
     * @param info
     */
    @Override
    public void OnDownloadStateChanged(DownloadInfo info) {
        //判断下载对象是否是当前应用
        AppInfo data = getData();
        if (data.getId().equals(info.id)) {
            refreshUIMainThread(info);
        }
    }

    /**
     * 进度更新
     *
     * @param info
     */
    @Override
    public void OnDownloadProgressChanged(DownloadInfo info) {
        AppInfo data = getData();
        if (data.getId().equals(info.id)) {
            refreshUIMainThread(info);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
            case R.id.fl_progress:
                // 根据当前状态来决定下一步操作
                if (mCurrentState == DownLoadManager.STATE_NONE
                        || mCurrentState == DownLoadManager.STATE_ERROR
                        || mCurrentState == DownLoadManager.STATE_PAUSE) {
                    mDm.download(getData());// 开始下载
                } else if (mCurrentState == DownLoadManager.STATE_DOWNLOAD
                        || mCurrentState == DownLoadManager.STATE_WAITING) {
                    mDm.pause(getData());// 暂停下载
                } else if (mCurrentState == DownLoadManager.STATE_SUCCESS) {
                    mDm.install(getData());// 开始安装
                }
                break;
            default:
                break;
        }
    }
}

