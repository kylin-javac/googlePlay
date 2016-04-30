package ligang.huse.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;

import ligang.huse.cn.googleplay.R;
import ligang.huse.cn.googleplay.domain.CategoryInfo;
import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.utils.BitmapsUtilsHelper;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 创建时间 javac on 2016/4/28.
 * <p/>
 * 文  件 googlePlay
 * <p/>
 * 描  述 普通类型填充数据
 */
public class CategoryHolder extends BaseHolder<CategoryInfo> implements View.OnClickListener {
    private LinearLayout mGrid1;
    private ImageView mImg1;
    private TextView mName1;
    private LinearLayout mGrid2;
    private ImageView mImg2;
    private TextView mName2;
    private LinearLayout mGrid3;
    private ImageView mImg3;
    private TextView mName3;
    private BitmapUtils mBitmap;


    @Override
    public View initView() {

        View view = UiUitls.inflat(R.layout.list_item_catergory);
        mGrid1 = (LinearLayout) view.findViewById(R.id.grid1);
        mImg1 = (ImageView) view.findViewById(R.id.img1);
        mName1 = (TextView) view.findViewById(R.id.name1);
        mGrid2 = (LinearLayout) view.findViewById(R.id.grid2);
        mImg2 = (ImageView) view.findViewById(R.id.img2);
        mName2 = (TextView) view.findViewById(R.id.name2);
        mGrid3 = (LinearLayout) view.findViewById(R.id.grid3);
        mImg3 = (ImageView) view.findViewById(R.id.img3);
        mName3 = (TextView) view.findViewById(R.id.name3);
        mBitmap = BitmapsUtilsHelper.getInstance();
        mGrid1.setOnClickListener(this);
        mGrid2.setOnClickListener(this);
        mGrid3.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        mName1.setText(data.getName1());
        mName2.setText(data.getName2());
        mName3.setText(data.getName3());
        mBitmap.display(mImg1, HttpHelper.URL+"image?name="+data.getUrl1());
        mBitmap.display(mImg2, HttpHelper.URL+"image?name="+data.getUrl2());
        mBitmap.display(mImg3, HttpHelper.URL+"image?name="+data.getUrl3());


    }

    @Override
    public void onClick(View v) {
        CategoryInfo data = getData();
        switch (v.getId()){
            case R.id.grid1:
                Toast.makeText(UiUitls.getContex(),data.getName1(),Toast.LENGTH_LONG).show();
                break;
            case R.id.grid2:
                Toast.makeText(UiUitls.getContex(),data.getName2(),Toast.LENGTH_LONG).show();
                break;
            case R.id.grid3:
                Toast.makeText(UiUitls.getContex(),data.getName3(),Toast.LENGTH_LONG).show();
                break;
        }
    }
}
