package ligang.huse.cn.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.utils.UiUitls;
import ligang.huse.cn.googleplay.ui.view.LoadingPager.ResultState;

/**
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPager mloadingPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 使用textview显示当前类的类名
        //TextView view = new TextView(UiUitls.getContex());
        //view.setText(getClass().getSimpleName());
        //继续让他的子类实现创建view
        mloadingPager = new LoadingPager(UiUitls.getContex()) {

            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();//继续让他的子类实现创建view
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        return mloadingPager;

    }


    public abstract ResultState onLoad();

    public abstract View onCreateSuccessView();

    // 开始加载数据
    public void loadData() {
        if (mloadingPager != null) {
            mloadingPager.loadData();
        }
    }

    public LoadingPager.ResultState check(Object obj) {
        if (obj != null) {
            if (obj instanceof ArrayList) {
                ArrayList list = (ArrayList) obj;
                if (list.isEmpty()) {
                    return LoadingPager.ResultState.STATE_EMPTY;
                } else {
                    return LoadingPager.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPager.ResultState.STATE_ERROR;
    }
}
