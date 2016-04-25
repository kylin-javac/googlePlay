package ligang.huse.cn.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * BitmapUtils的工厂类，避免重复new出BitampUtils对象导致oom
 */
public class BitmapsUtilsHelper {
    private static BitmapUtils bitmapUitls = null;

    public static synchronized BitmapUtils getInstance() {
        if (bitmapUitls == null) {
            bitmapUitls = new BitmapUtils(UiUitls.getContex());
        }
        return bitmapUitls;
    }

}
