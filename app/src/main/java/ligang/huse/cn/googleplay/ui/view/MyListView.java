package ligang.huse.cn.googleplay.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义listview
 */
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
        initView();
    }


    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setSelector(new ColorDrawable());//将默认选择器设为透明
        this.setDivider(null);//去掉分割线
        this.setCacheColorHint(Color.TRANSPARENT);//有时候listview滑动的时候会变成黑色,此方法是把滑动的背景设为透明
    }
}
