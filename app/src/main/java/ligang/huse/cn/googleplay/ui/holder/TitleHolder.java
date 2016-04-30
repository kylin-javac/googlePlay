package ligang.huse.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.CategoryInfo;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/28.
 * <p/>
 * 文  件 googlePlay
 * <p/>
 * 描  述 分类布局中的标题视图
 */
public class TitleHolder extends BaseHolder<CategoryInfo> {
    private TextView mTitle;


    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.list_item_title);
        mTitle = (TextView) view.findViewById(R.id.title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        mTitle.setText(data.getTitle());
    }
}
