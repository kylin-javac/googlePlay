package ligang.huse.cn.googleplay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.http.protocol.HomeDeailProtocol;
import ligang.huse.cn.googleplay.ui.holder.DetailAppInfoHolder;
import ligang.huse.cn.googleplay.ui.holder.DetailDesHolder;
import ligang.huse.cn.googleplay.ui.holder.DetailPicHolder;
import ligang.huse.cn.googleplay.ui.holder.DetailSafeHolder;
import ligang.huse.cn.googleplay.ui.view.LoadingPager;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/28.
 * <p>
 * 文  件 googlePlay
 * <p>
 * 描  述 首页应用 详情页
 */
public class HomeDetailAcivity extends BaseActivity {


    private LoadingPager mLoadingPager;
    private String mPackagName;
    private FrameLayout mFlDetailAppinfo;
    private AppInfo mData;
    private FrameLayout mFlDetailAppsafe;
    private HorizontalScrollView mHsv_detail_pics;
    private FrameLayout mFlDetailAppdes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingPager = new LoadingPager(this) {
            @Override
            public ResultState onLoad() {
                return HomeDetailAcivity.this.onLoad();
            }


            @Override
            public View onCreateSuccessView() {
                return HomeDetailAcivity.this.onCreateSuccessView();
            }
        };
        setContentView(mLoadingPager);//直接将一个view设置给activity
        mPackagName = getIntent().getStringExtra("packagName");//从AppInfo页面拿到包名
        mLoadingPager.loadData();
    }

    public View onCreateSuccessView() {
        View homeDeail = UiUitls.inflat(R.layout.page_home_detail);
        mFlDetailAppinfo = (FrameLayout) homeDeail.findViewById(R.id.fl_detail_appinfo);
        mFlDetailAppsafe = (FrameLayout)homeDeail.findViewById(R.id.fl_detail_appsafe);
        mHsv_detail_pics = (HorizontalScrollView) homeDeail.findViewById(R.id.hsv_detail_pics);
        mFlDetailAppdes = (FrameLayout) homeDeail.findViewById(R.id.fl_detail_appdes);

        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
        DetailSafeHolder safeHolder = new DetailSafeHolder();
        DetailPicHolder picHolder = new DetailPicHolder();
        DetailDesHolder desHolder = new DetailDesHolder();

        appInfoHolder.setData(mData);
        safeHolder.setData(mData);
        picHolder.setData(mData);
        desHolder.setData(mData);

        mFlDetailAppinfo.addView(appInfoHolder.getRootView());
        mFlDetailAppsafe.addView(safeHolder.getRootView());
        mHsv_detail_pics.addView(picHolder.getRootView());
        mFlDetailAppdes.addView(desHolder.getRootView());
        return homeDeail;
    }

    public LoadingPager.ResultState onLoad() {
        HomeDeailProtocol protocol = new HomeDeailProtocol(mPackagName);
        mData = protocol.getData(0);
        if (mData != null) {
            return LoadingPager.ResultState.STATE_SUCCESS;
        } else {
            return LoadingPager.ResultState.STATE_ERROR;
        }
    }

}
