package ligang.huse.cn.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * Created by 2 on 2016/4/17.
 */
public class BaseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 使用textview显示当前类的类名
        TextView view = new TextView(UiUitls.getContex());
        view.setText(getClass().getSimpleName());
        return view;

    }

}
