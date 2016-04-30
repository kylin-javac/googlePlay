package ligang.huse.cn.googleplay.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.ui.view.fly.RandomLayout;
import ligang.huse.cn.googleplay.utils.BitmapsUtilsHelper;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/28.
 * <p/>
 * 文  件 googlePlay
 * <p/>
 * 描  述 首页轮播条
 */
public class HomeHeadHolder extends BaseHolder<ArrayList<String>> {
    ArrayList<String> mData;
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private RelativeLayout mRLayout;
    private int pre;

    @Override
    public View initView() {

        //用代码的方式和新建一个相对布局
        mRLayout = new RelativeLayout(UiUitls.getContex());
        //指定相对布局的宽和高
        AbsListView.LayoutParams parms = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UiUitls.dipTopx(180));
        mRLayout.setLayoutParams(parms);
        //新建一个viewpager
        mViewPager = new ViewPager(UiUitls.getContex());
        //指定viewpager的宽和高
        RelativeLayout.LayoutParams rparms = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RandomLayout.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(rparms);
        mRLayout.addView(mViewPager);

        //添加指示器功能
        mLinearLayout = new LinearLayout(UiUitls.getContex());
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);//水平方向
        RelativeLayout.LayoutParams Llparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置显示的位置
        Llparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        Llparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        int padding = UiUitls.dipTopx(4);
        mLinearLayout.setPadding(padding, padding, padding, padding);
        mRLayout.addView(mLinearLayout, Llparams);

        return mRLayout;
    }

    @Override
    public void refreshView(ArrayList<String> data) {
        //填充viewpager的数据
        mData = data;
        mViewPager.setAdapter(new HomeViewpager());
        mViewPager.setCurrentItem(mData.size() * 100000);

        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UiUitls.getContex());
            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            if (i == 0) {
                point.setImageResource(R.drawable.indicator_selected);
            } else {
                point.setImageResource(R.drawable.indicator_normal);
                parms.leftMargin = UiUitls.dipTopx(4);
            }
            point.setLayoutParams(parms);
            mLinearLayout.addView(point);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % mData.size();
                ImageView point = (ImageView) mLinearLayout.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                //上个点变为不选中
                ImageView prePoint = (ImageView) mLinearLayout.getChildAt(HomeHeadHolder.this.pre);
                prePoint.setImageResource(R.drawable.indicator_normal);
                pre = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        HomeHeaderTask task = new HomeHeaderTask();
        task.start();

    }


    class HomeHeaderTask implements Runnable {
        public void start() {
            UiUitls.getHandle().removeCallbacksAndMessages(null);
            UiUitls.getHandle().postDelayed(this, 2000);

        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
            UiUitls.getHandle().postDelayed(this, 2000);

        }
    }


    class HomeViewpager extends PagerAdapter {

        private final BitmapUtils mBitmapUtils;

        public HomeViewpager() {
            mBitmapUtils = BitmapsUtilsHelper.getInstance();

        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mData.size();
            String url = mData.get(position);
            ImageView imageView = new ImageView(UiUitls.getContex());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(imageView, HttpHelper.URL + "image?name=" + url);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
