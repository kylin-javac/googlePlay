package ligang.huse.cn.googleplay.ui.holder;

import android.view.View;

/**
 * ViewHolder的基类
 */
public  abstract  class BaseHolder<T> {

    private  View mRootView;
    private T data;
    public BaseHolder(){
        //初始化布局，并查找控件
        mRootView = initView();
        //打标签
        mRootView.setTag(this);
    }

    public View getRootView(){
        return  mRootView;
    }

    public void setData(T data){
        this.data=data;
        refreshView(data);
    }
    public T getData(){
        return  data;
    }

    /**
     * 初始化布局
     * @return
     */
    public abstract View initView();

    /**
     * 刷新数据
     * @param data
     */
    public abstract void  refreshView(T data);

}
