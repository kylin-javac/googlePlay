package ligang.huse.cn.googleplay.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.http.protocol.AppProtocol;
import ligang.huse.cn.googleplay.ui.Adapter.MyBaseAdapter;
import ligang.huse.cn.googleplay.ui.holder.AppHolder;
import ligang.huse.cn.googleplay.ui.holder.BaseHolder;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.ui.view.MyListView;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 应用fragment
 */
public class AppFragment extends BaseFragment {
    private ArrayList<AppInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UiUitls.getContex());
        listView.setAdapter(new AppAdapter(mData));
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        AppProtocol protocol = new AppProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class AppAdapter extends MyBaseAdapter<AppInfo> {

        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder() {
            AppHolder holder = new AppHolder();
            return holder;
        }

        @Override
        public ArrayList<AppInfo> onloadMore() {
            AppProtocol protocol = new AppProtocol();
            return protocol.getData(getDataSize());
        }
    }
}
