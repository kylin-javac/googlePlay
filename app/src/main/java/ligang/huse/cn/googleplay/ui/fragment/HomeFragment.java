package ligang.huse.cn.googleplay.ui.fragment;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.http.protocol.HomeProtocol;
import ligang.huse.cn.googleplay.ui.Adapter.MyBaseAdapter;
import ligang.huse.cn.googleplay.ui.holder.BaseHolder;
import ligang.huse.cn.googleplay.ui.holder.HomeHolder;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.ui.view.MyListView;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 首页fragment
 */
public class HomeFragment extends BaseFragment {


    private ArrayList<AppInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UiUitls.getContex());
        listView.setAdapter(new HomeAdapter(mData));
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        HomeProtocol protocol = new HomeProtocol();
        mData = protocol.getData(0);

        return check(mData);
    }


    class HomeAdapter extends MyBaseAdapter<AppInfo> {
        public HomeAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder() {
            return new HomeHolder();
        }

        @Override
        public ArrayList<AppInfo> onloadMore() {
            HomeProtocol protocol = new HomeProtocol();
            return protocol.getData(getDataSize());
        }

        @Override
        public boolean hasMore() {
            return true;
        }
    }

    static class ViewHolder {
        TextView tv_content;
    }


}
