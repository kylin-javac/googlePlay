package ligang.huse.cn.googleplay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 应用fragment
 */
public class RecommenFragment extends BaseFragment {
    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UiUitls.getContex());
        view.setText(getClass().getSimpleName());
        return  view;
    }
    @Override
    public LoadingPager.ResultState onLoad() {
        return LoadingPager.ResultState.STATE_SUCCESS;
    }
}
