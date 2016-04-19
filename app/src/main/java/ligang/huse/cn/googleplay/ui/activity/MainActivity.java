package ligang.huse.cn.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.ui.fragment.BaseFragment;
import ligang.huse.cn.googleplay.ui.fragment.FragmentFactory;
import ligang.huse.cn.googleplay.ui.view.PagerTab;
import ligang.huse.cn.googleplay.utils.UiUitls;

public class MainActivity extends BaseActivity {


    private PagerTab mPagerTab;
    private ViewPager mViewPager;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPagerTab.setViewPager(mViewPager);
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory
                        .createFragment(position);
                // 开始加载数据
                fragment.loadData();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {

        private  String[] mArray;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mArray = UiUitls.getStringArray(R.array.tab_names);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mArray[position];
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment baseFragment = FragmentFactory.createFragment(position);
            return baseFragment;
        }

        @Override
        public int getCount() {
            return mArray.length;
        }
    }

}
