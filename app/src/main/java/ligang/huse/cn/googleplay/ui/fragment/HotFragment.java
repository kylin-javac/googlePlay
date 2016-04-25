package ligang.huse.cn.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import ligang.huse.cn.googleplay.http.protocol.HotProtocol;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.ui.view.fly.FlowLayout;
import ligang.huse.cn.googleplay.utils.DrawbleUitils;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 *排行fragment
 */
public class HotFragment extends BaseFragment {

    private ArrayList<String> mData;
    @Override
    public View onCreateSuccessView() {
        ScrollView scrollView = new ScrollView(UiUitls.getContex());
        FlowLayout flowLayout = new FlowLayout(UiUitls.getContex());
        int padding= UiUitls.dipTopx(10);
        flowLayout.setPadding(padding,padding,padding,padding);
        flowLayout.setHorizontalSpacing(UiUitls.dipTopx(6));
        flowLayout.setVerticalSpacing(UiUitls.dipTopx(6));
        for(int i=0;i<mData.size();i++){
            TextView view = new TextView(UiUitls.getContex());
            final String keyWord = mData.get(i);
            view.setText(keyWord);
            view.setPadding(padding,padding,padding,padding);
            Random random = new Random();
            int r=30+random.nextInt(200);
            int g=30+random.nextInt(200);
            int b=30+random.nextInt(200);
            int color=0xffcecece;
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            view.setTextColor(Color.WHITE);
           // GradientDrawable bgNormal = DrawbleUitils.getGradientDrawable(Color.rgb(r, g, b), UiUitls.dipTopx(6));
            StateListDrawable selectColor = DrawbleUitils.getSelectColor(Color.rgb(r, g, b), color, UiUitls.dipTopx(6));
            view.setBackgroundDrawable(selectColor);
            flowLayout.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UiUitls.getContex(),keyWord,Toast.LENGTH_LONG).show();
                }
            });
        }
        scrollView.addView(flowLayout);
        return scrollView;

    }
    @Override
    public LoadingPager.ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }


}
