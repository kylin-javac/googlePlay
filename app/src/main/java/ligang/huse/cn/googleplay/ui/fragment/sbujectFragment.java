package ligang.huse.cn.googleplay.ui.fragment;

import android.view.View;

import ligang.huse.cn.googleplay.ui.view.LoadingPager;

/**
 * 主题fragment
 */
public class sbujectFragment extends BaseFragment {
    @Override
    public LoadingPager.ResultState onLoad() {
        return LoadingPager.ResultState.STATE_ERROR;
    }

    @Override
    public View onCreateSuccessView() {
        return null;
    }
}
