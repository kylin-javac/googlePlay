package ligang.huse.cn.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.domain.DownloadInfo;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.manager.DownLoadManager;
import ligang.huse.cn.googleplay.ui.view.ProgressArc;
import ligang.huse.cn.googleplay.utils.BitmapsUtilsHelper;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * Created by 2 on 2016/4/20.
 */
public class HomeHolder extends BaseHolder<AppInfo> implements
        DownLoadManager.DownloadObserver, View.OnClickListener {
    private ImageView mIvCon;
    private TextView mTvName;
    private TextView mTvSize;
    private ImageView mIvDownload;
    private TextView mDesc;
    private RatingBar mRbStart;
    private BitmapUtils mBitmapUtils;
    private DownLoadManager mDM;
    private ProgressArc pbProgress;
    private int mCurrentState;
    private float mProgress;


    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.list_item_home);
        mIvCon = (ImageView) view.findViewById(R.id.iv_con);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvSize = (TextView) view.findViewById(R.id.tv_size);
        mRbStart = (RatingBar) view.findViewById(R.id.rb_start);
        mDesc = (TextView) view.findViewById(R.id.desc);

        mBitmapUtils = BitmapsUtilsHelper.getInstance();
        FrameLayout flProgress = (FrameLayout) view
                .findViewById(R.id.fl_progress);
        flProgress.setOnClickListener(this);
        pbProgress = new ProgressArc(UiUitls.getContex());
        // 设置圆形进度条直径
        pbProgress.setArcDiameter(UiUitls.dipTopx(26));
        // 设置进度条颜色
        pbProgress.setProgressColor(UiUitls.getColor(R.color.progress));
        // 设置进度条宽高布局参数
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                UiUitls.dipTopx(27), UiUitls.dipTopx(27));
        flProgress.addView(pbProgress, params);

        // pbProgress.setOnClickListener(this);

        mDM = DownLoadManager.getInstance();
        mDM.registerObserver(this);// 注册观察者, 监听状态和进度变化
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mTvName.setText(data.getName());
        mTvSize.setText(Formatter.formatFileSize(UiUitls.getContex(), data.getSize()));
        mRbStart.setRating((float) data.getStars());
        //加载图片
        mBitmapUtils.display(mIvCon, HttpHelper.URL + "image?name=" + data.getIconUrl());
        mDesc.setText(data.getDes());

        // 判断当前应用是否下载过
        DownloadInfo downloadInfo = mDM.getDownloadInfo(data);
        if (downloadInfo != null) {
            // 之前下载过
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        } else {
            // 没有下载过
            mCurrentState = DownLoadManager.STATE_NONE;
            mProgress = 0;
        }

        refreshUI(mCurrentState, mProgress, data.getId());

    }

    private void refreshUI(int state, float progress, String id) {
        // 由于listview重用机制, 要确保刷新之前, 确实是同一个应用
        if (!getData().getId().equals(id)) {
            return;
        }

        mCurrentState = state;
        mProgress = progress;
        switch (state) {
            case DownLoadManager.STATE_NONE:
                // 自定义进度条背景
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                // 没有进度
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mTvName.setText("下载");
                break;
            case DownLoadManager.STATE_WAITING:
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                // 等待模式
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                mTvName.setText("等待");
                break;
            case DownLoadManager.STATE_DOWNLOAD:
                pbProgress.setBackgroundResource(R.drawable.ic_pause);
                // 下载中模式
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                pbProgress.setProgress(progress, true);
                mTvName.setText((int) (progress * 100) + "%");
                break;
            case DownLoadManager.STATE_PAUSE:
                pbProgress.setBackgroundResource(R.drawable.ic_resume);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                break;
            case DownLoadManager.STATE_ERROR:
                pbProgress.setBackgroundResource(R.drawable.ic_redownload);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mTvName.setText("下载失败");
                break;
            case DownLoadManager.STATE_SUCCESS:
                pbProgress.setBackgroundResource(R.drawable.ic_install);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mTvName.setText("安装");
                break;

            default:
                break;
        }
    }

    @Override
    public void OnDownloadStateChanged(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }

    @Override
    public void OnDownloadProgressChanged(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }


    // 主线程更新ui 3-4
    private void refreshUIOnMainThread(final DownloadInfo info) {
        // 判断下载对象是否是当前应用
        AppInfo appInfo = getData();
        if (appInfo.getId().equals(info.id)) {
            UiUitls.runUiThread(new Runnable() {

                @Override
                public void run() {
                    refreshUI(info.currentState, info.getProgress(), info.id);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_progress:
                // 根据当前状态来决定下一步操作
                if (mCurrentState == DownLoadManager.STATE_NONE
                        || mCurrentState == DownLoadManager.STATE_ERROR
                        || mCurrentState == DownLoadManager.STATE_PAUSE) {
                    mDM.download(getData());// 开始下载
                } else if (mCurrentState == DownLoadManager.STATE_DOWNLOAD
                        || mCurrentState == DownLoadManager.STATE_WAITING) {
                    mDM.pause(getData());// 暂停下载
                } else if (mCurrentState == DownLoadManager.STATE_SUCCESS) {
                    mDM.install(getData());// 开始安装
                }
                break;
            default:
                break;
        }
    }
}

