package ligang.huse.cn.googleplay.ui.holder;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.utils.BitmapsUtilsHelper;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/29.
 * <p/>
 * 文  件 googlePlay
 * <p/>
 * 描  述 应用详情页 安全模块
 */
public class DetailSafeHolder extends BaseHolder<AppInfo> {
    private ImageView[] mSafeIcons;// 安全标识图片
    private ImageView[] mDesIcons;// 安全描述图片
    private TextView[] mSafeDes;// 安全描述文字
    private LinearLayout[] mSafeDesBar;// 安全描述条目(图片+文字)
    private BitmapUtils mBitmapUtils;
    private RelativeLayout mRlDesRoot;
    private LinearLayout mLlDesRoot;
    private LinearLayout.LayoutParams mParams;
    private int mHeight;
    private ImageView mIv_arrow;

    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.layout_detail_safeinfo);

        mSafeIcons = new ImageView[4];
        mSafeIcons[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        mSafeIcons[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        mSafeIcons[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        mSafeIcons[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        mDesIcons = new ImageView[4];
        mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
        mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
        mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
        mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

        mSafeDes = new TextView[4];
        mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

        mSafeDesBar = new LinearLayout[4];
        mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);
        mRlDesRoot = (RelativeLayout) view.findViewById(R.id.rl_des_root);
        mRlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        mLlDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);
        mIv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        mBitmapUtils = BitmapsUtilsHelper.getInstance();
        return view;
    }

    private boolean isOpen = false;

    //// 打开或者关闭安全描述信息
    public void toggle() {
        ValueAnimator animator = null;
        if (isOpen) {
            isOpen = false;
            animator = ValueAnimator.ofInt(0, mHeight);// 从某个值变化到某个值
        } else {
            isOpen = true;
            animator = ValueAnimator.ofInt(mHeight, 0);
        }
        // 动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            // 启动动画之后, 会不断回调此方法来获取最新的值
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                // 获取最新的高度值
                Integer height = (Integer) animation.getAnimatedValue();
                // 重新修改布局高度
                mParams.height = height;
                //Log.i("Interger000 ::::", height + "");
                mLlDesRoot.setLayoutParams(mParams);

            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束的事件
                // 更新小箭头的方向
                if (isOpen) {
                    mIv_arrow.setImageResource(R.drawable.arrow_1up);
                } else {
                    mIv_arrow.setImageResource(R.drawable.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        animator.setDuration(200);// 动画时间
        animator.start();// 启动动画

    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<AppInfo.SafeInfo> safe = data.getSafe();
        for (int i = 0; i < 4; i++) {
            if (i < safe.size()) {
                AppInfo.SafeInfo safeInfo = safe.get(i);
                // 安全标识图片
                mBitmapUtils.display(mSafeIcons[i], HttpHelper.URL + "image?name=" + safeInfo.getSafeurl());
                // 安全描述图片
                mBitmapUtils.display(mDesIcons[i], HttpHelper.URL + "image?name=" + safeInfo.getSafeDesul());
                // 安全描述文字
                mSafeDes[i].setText(safeInfo.getSafe());
            } else {
                //隐藏不应该显示图片的位置
                mDesIcons[i].setVisibility(View.GONE);
                mSafeIcons[i].setVisibility(View.GONE);
            }
        }
        // 获取安全描述的完整高度
        mLlDesRoot.measure(0, 0);
        mHeight = mLlDesRoot.getMeasuredHeight();
        // Log.i("Interger", mHeight + "");
        //修改安全描述布局高度为0,达到隐藏效果
        mParams = (LinearLayout.LayoutParams) mLlDesRoot.getLayoutParams();
        mParams.height = 0;
        mLlDesRoot.setLayoutParams(mParams);
    }
}
