package ligang.huse.cn.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.utils.BitmapsUtilsHelper;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/29.
 * <p>
 * 文  件 googlePlay
 * <p>
 * 描  述 详情页 应用信息
 */
public class DetailAppInfoHolder extends BaseHolder<AppInfo> {
    private ImageView mIvIcon;
    private TextView mTvName;
    private RatingBar mRbStar;
    private TextView mTvDownloadNum;
    private TextView mTvVersion;
    private TextView mTvDate;
    private TextView mTvSize;
    private BitmapUtils mBitmapUtils;


    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.layout_detail_appinfo);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mRbStar = (RatingBar) view.findViewById(R.id.rb_star);
        mTvDownloadNum = (TextView) view.findViewById(R.id.tv_download_num);
        mTvVersion = (TextView) view.findViewById(R.id.tv_version);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvSize = (TextView) view.findViewById(R.id.tv_size);
        mBitmapUtils = BitmapsUtilsHelper.getInstance();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mBitmapUtils.display(mIvIcon, HttpHelper.URL + "image?name=" + data.getIconUrl());
        mTvName.setText(data.getName());
        mRbStar.setRating((float) data.getStars());
        mTvDownloadNum.setText("下载量:" + data.getDownloadNum());
        mTvVersion.setText("版本号:" + data.getVersion());
        mTvDate.setText(data.getDate());
        mTvSize.setText(Formatter.formatFileSize(UiUitls.getContex(), data.getSize()));

    }
}
