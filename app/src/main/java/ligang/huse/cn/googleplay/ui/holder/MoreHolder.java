package ligang.huse.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 加载更多（就这一个大的基类）
 */
public class MoreHolder extends  BaseHolder<Integer> {
    public static final int STATE_MORE_MORE=1;//加载更多
    public static final int STATE_MORE_ERROR=2;//加载失败
    public static final int STATE_MORE_NONE=3;//加载
    private LinearLayout mLl;
    private TextView mLoadfaild;

    public MoreHolder(boolean hasMore) {
        setData(hasMore?STATE_MORE_MORE:STATE_MORE_NONE);
        //setData(STATE_MORE_ERROR);
    }


    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.list_item_more);
        mLl = (LinearLayout) view.findViewById(R.id.ll);
        mLoadfaild = (TextView) view.findViewById(R.id.loadfaild);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data){
            case STATE_MORE_MORE:
                mLl.setVisibility(View.VISIBLE);
                mLoadfaild.setVisibility(View.GONE);
                break;
            case STATE_MORE_ERROR:
                mLoadfaild.setVisibility(View.VISIBLE);
                mLl.setVisibility(View.GONE);
                break;
            case STATE_MORE_NONE:
                mLoadfaild.setVisibility(View.GONE);
                mLl.setVisibility(View.GONE);
                break;
        }

    }
}
