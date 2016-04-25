package ligang.huse.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.SubjectInfo;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.utils.BitmapsUtilsHelper;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * Created by 2 on 2016/4/22.
 */
public class SubjectHolder extends BaseHolder<SubjectInfo> {

    private ImageView mIvPic;
    private TextView mTvTitle;
    private BitmapUtils mBitma;


    @Override
    public View initView() {
        View view = View.inflate(UiUitls.getContex(), R.layout.list_item_subject, null);
        mIvPic = (ImageView) view.findViewById(R.id.iv_pic);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mBitma = BitmapsUtilsHelper.getInstance();
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        mTvTitle.setText(data.getDes());
        mBitma.display(mIvPic, HttpHelper.URL+"image?name="+data.getUrl());
    }
}
