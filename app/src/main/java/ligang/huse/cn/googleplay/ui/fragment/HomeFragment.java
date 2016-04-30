package ligang.huse.cn.googleplay.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.http.protocol.HomeProtocol;
import ligang.huse.cn.googleplay.ui.Adapter.MyBaseAdapter;
import ligang.huse.cn.googleplay.ui.activity.HomeDetailAcivity;
import ligang.huse.cn.googleplay.ui.holder.BaseHolder;
import ligang.huse.cn.googleplay.ui.holder.HomeHeadHolder;
import ligang.huse.cn.googleplay.ui.holder.HomeHolder;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.ui.view.MyListView;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 首页fragment
 */
public class HomeFragment extends BaseFragment {


    private ArrayList<AppInfo> mData;
    private ArrayList<String> mPicture;

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UiUitls.getContex());
        //给listview增加头布局，展示轮播条
        HomeHeadHolder homeHead = new HomeHeadHolder();
        listView.addHeaderView(homeHead.getRootView());
        if(mPicture!=null){
            homeHead.setData(mPicture);
        }
        listView.setAdapter(new HomeAdapter(mData));
        //给每个选项设置点击事件，进入每个app的详情页
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = mData.get(position-1);
                if(appInfo!=null) {
                    Intent intent = new Intent(UiUitls.getContex(), HomeDetailAcivity.class);
                    intent.putExtra("packagName", appInfo.getPackageName());
                    Log.i("-------->", appInfo.getPackageName());
                    startActivity(intent);
                }
            }
        });
        return listView;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        HomeProtocol protocol = new HomeProtocol();
        mData = protocol.getData(0);
        mPicture = protocol.getPicture();
        return check(mData);
    }


    class HomeAdapter extends MyBaseAdapter<AppInfo> {
        public HomeAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
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
