package ligang.huse.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.AppInfo;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.utils.BitmapsUtilsHelper;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/30.
 * <p>
 * 文  件 googlePlay
 * <p>
 * 描  述 首页详情页  截图
 */
public class DetailPicHolder extends  BaseHolder<AppInfo> {
    private ImageView[] ivPics;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view =UiUitls.inflat(R.layout.layout_detail_picinfo);
        ivPics = new ImageView[5];
        ivPics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        ivPics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        ivPics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        ivPics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        ivPics[4] = (ImageView) view.findViewById(R.id.iv_pic5);
        mBitmapUtils = BitmapsUtilsHelper.getInstance();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<String> screen = data.getScreen();
        for(int i=0;i<5;i++){
            if(i<screen.size()){
                mBitmapUtils.display(ivPics[i], HttpHelper.URL+"image?name="+screen.get(i));
            }else{
                ivPics[i].setVisibility(View.GONE);
            }
        }

    }
}
