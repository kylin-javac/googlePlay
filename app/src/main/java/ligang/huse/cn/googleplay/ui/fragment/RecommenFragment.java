package ligang.huse.cn.googleplay.ui.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import ligang.huse.cn.googleplay.http.protocol.RecommendProtocol;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.ui.view.fly.ShakeListener;
import ligang.huse.cn.googleplay.ui.view.fly.StellarMap;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 应用fragment
 */
public class RecommenFragment extends BaseFragment {

    private ArrayList<String> mData;
    private StellarMap mStellarMap;

    @Override
    public View onCreateSuccessView() {
        mStellarMap = new StellarMap(UiUitls.getContex());
        mStellarMap.setAdapter(new RecommendAdapter());
        mStellarMap.setRegularity(6, 9);
        int padding = UiUitls.dipTopx(10);
        mStellarMap.setInnerPadding(padding, padding, padding, padding);
        mStellarMap.setGroup(0, true);
        return mStellarMap;
    }

    @Override
    public LoadingPager.ResultState onLoad() {
        RecommendProtocol protocol = new RecommendProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class RecommendAdapter implements StellarMap.Adapter {
        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            int count = mData.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                count += mData.size() % getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            position += (group) * getCount(group - 1);
            TextView view = new TextView(UiUitls.getContex());
            final String keyWord = mData.get(position);
            view.setText(keyWord);

            Random random = new Random();
            //随机设置字体大小
            int size = 16 + random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
            //随机设置字体颜色
            int r=30+random.nextInt(200);
            int g=30+random.nextInt(200);
            int b=30+random.nextInt(200);
            view.setTextColor(Color.rgb(r,b,g));

            //摇一摇之后调到下一页数据
            ShakeListener shakeListener = new ShakeListener(UiUitls.getContex());
            shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
                @Override
                public void onShake() {
                   mStellarMap.zoomIn();//调到下一页数据
                }
            });
            //给每个条目添加点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UiUitls.getContex(),keyWord,Toast.LENGTH_LONG).show();
                }
            });
            return view;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn) {
                if (group > 0) {
                    group--;
                } else {
                    group = getGroupCount() - 1;
                }
            } else {
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    group = 0;
                }
            }

            return group;
        }
    }
}
