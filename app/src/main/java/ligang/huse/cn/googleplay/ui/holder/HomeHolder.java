package ligang.huse.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * Created by 2 on 2016/4/20.
 */
public class HomeHolder extends BaseHolder<AppInfo>{

    private TextView mTv_content;

    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.list_item_home);
        mTv_content = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mTv_content.setText(data.getName());

    }


}
