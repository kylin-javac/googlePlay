package ligang.huse.cn.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by 2 on 2016/4/25.
 */
public class DrawbleUitils {
    public static GradientDrawable  getGradientDrawable(int color,int radius){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static  StateListDrawable getSelectColor(Drawable normal,Drawable press){
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed},press);
        selector.addState(new int[]{},normal);
        return selector;

    }
    public static  StateListDrawable getSelectColor(int  normal,int press,int radius){
        GradientDrawable bgNormal = getGradientDrawable(normal, press);
        GradientDrawable bgPressd = getGradientDrawable(press, radius);
        StateListDrawable selectColor = getSelectColor(bgNormal, bgPressd);
        return selectColor;

    }
}
