package ligang.huse.cn.googleplay.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.SubjectInfo;
import ligang.huse.cn.googleplay.http.protocol.SubjectProtocol;
import ligang.huse.cn.googleplay.ui.Adapter.MyBaseAdapter;
import ligang.huse.cn.googleplay.ui.holder.BaseHolder;
import ligang.huse.cn.googleplay.ui.holder.SubjectHolder;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.ui.view.MyListView;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 主题fragment
 */
public class sbujectFragment extends BaseFragment {
    private ArrayList<SubjectInfo> mData;
    private SubjectProtocol mSubjectProtocol;

    @Override
    public LoadingPager.ResultState onLoad() {
        mSubjectProtocol = new SubjectProtocol();
        mData = mSubjectProtocol.getData(0);
        return check(mData);
    }

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UiUitls.getContex());
        listView.setAdapter(new SubjectAdapter(mData));
        return listView;
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder() {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onloadMore() {
            return mSubjectProtocol.getData(getDataSize());
        }
    }
}
