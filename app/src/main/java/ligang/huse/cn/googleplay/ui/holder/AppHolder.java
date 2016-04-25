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
 * Created by 2 on 2016/4/20.
 */
public class AppHolder extends BaseHolder<AppInfo> {
    private ImageView mIvCon;
    private TextView mTvName;
    private TextView mTvSize;
    private ImageView mIvDownload;
    private TextView mDesc;
    private RatingBar mRbStart;
    private BitmapUtils mBitmapUtils;



    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.list_item_home);
        mIvCon = (ImageView) view.findViewById(R.id.iv_con);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvSize = (TextView) view.findViewById(R.id.tv_size);
        mIvDownload = (ImageView) view.findViewById(R.id.iv_download);
        mRbStart = (RatingBar) view.findViewById(R.id.rb_start);
        mDesc = (TextView) view.findViewById(R.id.desc);
        mBitmapUtils = BitmapsUtilsHelper.getInstance();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mTvName.setText(data.getName());
        mTvSize.setText(Formatter.formatFileSize(UiUitls.getContex(), data.getSize()));
        mRbStart.setRating((float) data.getStars());
        //加载图片
        mBitmapUtils.display(mIvCon, HttpHelper.URL+"image?name="+data.getIconUrl());
        mDesc.setText(data.getDes());

    }


}
