package ligang.huse.cn.googleplay.ui.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.ui.holder.BaseHolder;
import ligang.huse.cn.googleplay.ui.holder.MoreHolder;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * ListView适配器的基类
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private ArrayList<T> data;
    public static final int TYPE_NORMAL = 0;//正常加载
    public static final int TYPE_MORE = 1;//加载更多

    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getViewTypeCount() {//listview中的类型个数
        return 2;
    }


    @Override
    public int getItemViewType(int position) {//返回当前位置显示哪种布局
        if (position == getCount() - 1) {
            return TYPE_MORE;
        } else {
            return getInnerType();
        }
    }


    public int getInnerType() {//对外提供一个返回类型的方法，因为有时候listview中不止显示两种布局文件
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder Holder;
        if (convertView == null) {
            if (getItemViewType(position) == TYPE_MORE) {
                Holder = new MoreHolder(hasMore());
            } else {
                //加载视图，并findViewById查找控件
                Holder = getHolder();
            }

        } else {
            //打标记
            Holder = (BaseHolder) convertView.getTag();
        }
        if (getItemViewType(position) != TYPE_MORE) {
            //加载数据
            Holder.setData(getItem(position));
        } else {
            MoreHolder moreHolder= (MoreHolder) Holder;
            if(moreHolder!=null){
                loadMore(moreHolder);
            }
        }

        //返回视图
        return Holder.getRootView();
    }
    private boolean isloadMore=false;
    //加载更多数据
    public void loadMore(final MoreHolder moreHolder){
        if(!isloadMore) {
            isloadMore=true;
            new Thread() {
                @Override
                public void run() {
                    final ArrayList<T> moreData = onloadMore();
                    UiUitls.runUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                if (moreData.size() < 20) {
                                    moreHolder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UiUitls.getContex(), "没有更多数据了", Toast.LENGTH_LONG).show();
                                } else {
                                    //还有更多数据
                                    moreHolder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                data.addAll(moreData);//将更多的数据添加到集合中
                                MyBaseAdapter.this.notifyDataSetChanged();//刷新界面
                            } else {
                                moreHolder.setData(MoreHolder.STATE_MORE_ERROR);//更多数据加载失败
                            }
                            isloadMore=false;
                        }
                    });

                }
            }.start();
        }

    }

    public boolean hasMore(){
        return  false;
    }

    public abstract BaseHolder<T> getHolder();
    public abstract ArrayList<T> onloadMore();

    public int getDataSize(){
        return  data.size();
    }
}
