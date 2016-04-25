package ligang.huse.cn.googleplay.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import ligang.huse.cn.googleplay.R;

/**
 * 自定义控件，按照比例决定控件高度
 */
public class RationLayout extends FrameLayout {


    private float mRatio;

    public RationLayout(Context context) {
        super(context);
    }

    public RationLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        //自定义属性的id通过R.styleable来引用
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        //自定义属性中的字段名(属性)通过自定义属性的具体id_+字段名来得到
        mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        typedArray.recycle();
        // float ratio = attrs.getAttributeFloatValue("", "ratio", -1);
        Log.i("------->", "ratio    " + mRatio);

    }

    public RationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /***
     * 对控件重新绘制测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int withSize = MeasureSpec.getSize(widthMeasureSpec);//获取控件宽度值
        int withMode = MeasureSpec.getMode(widthMeasureSpec);//获取控件宽度模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //MeasureSpec.AT_MOST;至多模式,控件中的子控件有多大显示多大
        //MeasureSpec.EXACTLY;确定模式，控件中的子控件的宽高与控件的宽高相匹配
        //MeasureSpec.UNSPECIFIED;未指定模式

        //首先控件的宽度确定，高度不确定，比例也要合法,才可以按照比例进行显示
        if (withMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && mRatio > 0) {
            //图片的宽度应该是减去左右间距的
            int imgeWidth = withSize - getPaddingLeft() - getPaddingRight();
            //按照比例计算图片的高度
            int imgHeight = (int) (imgeWidth / mRatio + 0.5f);
            //而控件高度是等于等于图片的高度+上下两部分间距的距离
            heightSize = imgHeight + getPaddingTop() + getPaddingBottom();
            //将计算出来控件的高度重新设置一下他的高度与模式
            heightSize = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        //最后super加载宽高时，将新的高度更新
        super.onMeasure(widthMeasureSpec, heightSize);
    }
}
