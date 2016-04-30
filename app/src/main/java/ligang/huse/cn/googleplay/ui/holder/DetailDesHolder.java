package ligang.huse.cn.googleplay.ui.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/30.
 * <p/>
 * 文  件 googlePlay
 * <p/>
 * 描  述 首页详情页 描述
 */
public class DetailDesHolder extends BaseHolder<AppInfo> {
    private TextView mTvDetailDes;
    private TextView mTvDetailAuthor;
    private ImageView mIvArrow;
    private RelativeLayout mRlDetailToggle;
    private LinearLayout.LayoutParams mParams;


    @Override
    public View initView() {
        View view = UiUitls.inflat(R.layout.layout_detail_desinfo);
        mTvDetailDes = (TextView) view.findViewById(R.id.tv_detail_des);
        mTvDetailAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        mIvArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        mRlDetailToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);
        mRlDetailToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();

            }
        });
        return view;
    }

    private boolean IsOpen = false;

    public void toggle() {
        ValueAnimator animator = null;
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        if (IsOpen) {
            IsOpen = false;
            if (longHeight > shortHeight) {//首先判断一下文本的实际长度要大于设定的7行的高度，我才做显示的动画
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
            }
        } else {
            IsOpen = true;
            if (longHeight > shortHeight) {
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }
        if (animator != null) {
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer height = (Integer) animation.getAnimatedValue();
                    mParams.height = height;
                    mTvDetailDes.setLayoutParams(mParams);
                }
            });

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    final ScrollView scrollView = getScrollView();
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);//跳转到scrollView的底部

                        }
                    });
                    //切换上下的图片
                    if (IsOpen) {
                        mIvArrow.setImageResource(R.drawable.arrow_1up);
                    } else {
                        mIvArrow.setImageResource(R.drawable.arrow_down);
                    }

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });


            animator.setDuration(200);
            animator.start();
        }



    }


    public ScrollView getScrollView() {
        ViewParent parent = mTvDetailDes.getParent();
        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }
        return (ScrollView) parent;
    }

    @Override
    public void refreshView(AppInfo data) {
        mTvDetailDes.setText(data.getDes());
        mTvDetailAuthor.setText(data.getAuthor());
        //放在消息队列中可以解决只有三行却显示7行高度的bug
       mTvDetailDes.post(new Runnable() {
           @Override
           public void run() {
               int shortHeight = getShortHeight();//得到显示7行的高度
               //拿到显示文本控件的布局参数
               mParams = (LinearLayout.LayoutParams) mTvDetailDes.getLayoutParams();
               mParams.height = shortHeight;//将显示7行的高度赋值给文本控件
               mTvDetailDes.setLayoutParams(mParams);//将设置好高度后的布局参数再设置给文本控件
           }
       });
    }

    /**
     * 获取7行texteView的高度
     *
     * @return
     */
    public int getShortHeight() {
        TextView textView = new TextView(UiUitls.getContex());
        int width = mTvDetailDes.getMeasuredWidth();
        textView.setText(getData().getDes());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//设置字体大小
        textView.setMaxLines(7);//设置最大显示行数
        int WidthmeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，设置固定值
        int HeightmakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高为设置最大值，有多大显示多大
        textView.measure(WidthmeasureSpec, HeightmakeMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * 获取texteView的高度
     *
     * @return
     */
    public int getLongHeight() {
        TextView textView = new TextView(UiUitls.getContex());
        int width = mTvDetailDes.getMeasuredWidth();
        textView.setText(getData().getDes());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//设置字体大小
        //textView.setMaxLines(7);//设置最大显示行数
        int WidthmeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，设置固定值
        int HeightmakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高为设置最大值，有多大显示多大
        textView.measure(WidthmeasureSpec, HeightmakeMeasureSpec);
        return textView.getMeasuredHeight();
    }

}
