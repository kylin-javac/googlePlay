package ligang.huse.cn.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 自定义初始化布局
 */
public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_LOADE_UNDO = 1;//未加载
    public static final int STATE_LOADE_LOADING = 2;//加载中
    public static final int STATE_LOADE_ERROR = 3;//加载出错
    public static final int STATE_LOADE_EMPTY = 4;//加载为空
    public static final int STATE_LOADE_SUCCESS = 5;//加载成功
    private int mCurrentState = STATE_LOADE_UNDO;//初始化状态
    private View mLodingPager;
    private View mLodingerror;
    private View mMLoadingempty;
    private View mSuccessView;


    public LoadingPager(Context context) {
        super(context);
        initView();
    }


    public LoadingPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mLodingPager == null) {
            mLodingPager = UiUitls.inflat(R.layout.pager_loading);
            addView(mLodingPager);//将布局文件添加到Loadingpager中去
        }

        if (mLodingerror == null) {
            mLodingerror = UiUitls.inflat(R.layout.pager_error);
            addView(mLodingerror);
            Button btn_retry = (Button) findViewById(R.id.btn_retry);
            //加载失败后重新加载数据
            btn_retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        }

        if (mMLoadingempty == null) {
            mMLoadingempty = UiUitls.inflat(R.layout.pager_empty);
            addView(mMLoadingempty);
        }
        showRightPager();//根据当前状态决定显示哪个布局

    }

    private void showRightPager() {

        mLodingPager.setVisibility((mCurrentState == STATE_LOADE_UNDO || mCurrentState == STATE_LOADE_LOADING) ? View.VISIBLE
                : View.GONE);

        mLodingerror.setVisibility(mCurrentState == STATE_LOADE_ERROR ? View.VISIBLE
                : View.GONE);

        mMLoadingempty.setVisibility(mCurrentState == STATE_LOADE_EMPTY ? View.VISIBLE
                : View.GONE);


        // 当成功布局为空,并且当前状态为成功,才初始化成功的布局
        if (mSuccessView == null && mCurrentState == STATE_LOADE_SUCCESS) {
            mSuccessView = onCreateSuccessView();
            if (mSuccessView != null) {
                addView(mSuccessView);
            }
        }

        if (mSuccessView != null) {
            mSuccessView
                    .setVisibility(mCurrentState == STATE_LOADE_SUCCESS ? View.VISIBLE
                            : View.GONE);
        }
    }

    // 开始加载数据
    public void loadData() {
        if (mCurrentState != STATE_LOADE_LOADING) {// 如果当前没有加载, 就开始加载数据

            mCurrentState = STATE_LOADE_LOADING;

            new Thread() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();

                    // 运行在主线程
                    UiUitls.runUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState();// 网络加载结束后,更新网络状态
                                // 根据最新的状态来刷新页面
                                showRightPager();
                            }
                        }
                    });
                }
            }.start();
        }
    }


    public abstract View onCreateSuccessView();//让子类实现去创建view

    public abstract ResultState onLoad();// 加载网络数据, 返回值表示请求网络结束后的状态


    public enum ResultState {
        STATE_SUCCESS(STATE_LOADE_SUCCESS), STATE_EMPTY(STATE_LOADE_EMPTY), STATE_ERROR(
                STATE_LOADE_ERROR);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }

    }
}

