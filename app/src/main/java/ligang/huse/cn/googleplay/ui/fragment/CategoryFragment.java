package ligang.huse.cn.googleplay.ui.fragment;

import android.view.View;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.CategoryInfo;
import ligang.huse.cn.googleplay.http.protocol.CategoryProtocol;
import ligang.huse.cn.googleplay.ui.Adapter.MyBaseAdapter;
import ligang.huse.cn.googleplay.ui.holder.BaseHolder;
import ligang.huse.cn.googleplay.ui.holder.CategoryHolder;
import ligang.huse.cn.googleplay.ui.holder.TitleHolder;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.ui.view.MyListView;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 分类fragment
 */
public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> mData;

    @Override
    public LoadingPager.ResultState onLoad() {
        CategoryProtocol protocol = new CategoryProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    @Override
    public View onCreateSuccessView() {

        MyListView listView = new MyListView(UiUitls.getContex());
        listView.setAdapter(new CategoryListView(mData));
        return listView;
    }

    class CategoryListView extends MyBaseAdapter<CategoryInfo> {

        public CategoryListView(ArrayList<CategoryInfo> data) {
            super(data);
        }



        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;//在原来的基础上加1
        }

        /**
         * 判断标题是普通类型还是标题类型
         *
         * @param postion
         * @return
         */

        @Override
        public int getInnerType(int postion) {

            CategoryInfo info = mData.get(postion);
            boolean isTitle = info.isTitle();
            if (isTitle) {
                //返回标题类型
                return super.getInnerType(postion) + 1;
            } else {
                //返回普通类型
                return super.getInnerType(postion);
            }
        }

        @Override
        public boolean hasMore() {
            return true;//没有更多数据,需要隐藏数据
        }

        @Override
        public BaseHolder<CategoryInfo> getHolder(int postion) {
            CategoryInfo info = mData.get(postion);
            if (info.isTitle()) {
                return new TitleHolder();
            } else {
                return new CategoryHolder();
            }
        }
        @Override
        public ArrayList<CategoryInfo> onloadMore() {
            return null;
        }
    }
}
