package ligang.huse.cn.googleplay.http.protocol;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.AppInfo;

/**
 * 创建时间 javac on 2016/4/28.
 * <p/>
 * 文  件 googlePlay
 * <p/>
 * 描  述 首页详情页的网络解析
 */
public class HomeDeailProtocol extends BaseProtocol<AppInfo> {
    private String packageName;

    public HomeDeailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public AppInfo ParseData(String result) {
        try {
            JSONObject jO1 = new JSONObject(result);
            AppInfo info = new AppInfo();
            info.setDes(jO1.getString("des"));
            info.setDownloadUrl(jO1.getString("downloadUrl"));
            info.setIconUrl(jO1.getString("iconUrl"));
            info.setId(jO1.getString("id"));
            info.setName(jO1.getString("name"));
            info.setPackageName(jO1.getString("packageName"));
            info.setSize(jO1.getLong("size"));
            info.setStars(jO1.getDouble("stars"));

            //解析新增的属性
            info.setAuthor(jO1.getString("author"));
            info.setDate(jO1.getString("date"));
            info.setDownloadNum(jO1.getString("downloadNum"));
            info.setVersion(jO1.getString("version"));

            //解析安全内容
            JSONArray ja = jO1.getJSONArray("safe");
            ArrayList<AppInfo.SafeInfo> safeInfos = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject ja1 = ja.getJSONObject(i);
                AppInfo.SafeInfo safeInfo = new AppInfo.SafeInfo();
                safeInfo.setSafe(ja1.getString("safeDes"));
                safeInfo.setSafeDesul(ja1.getString("safeDesUrl"));
                safeInfo.setSafeurl(ja1.getString("safeUrl"));
                safeInfos.add(safeInfo);
            }
            info.setSafe(safeInfos);

            //解析屏幕截图片地址
            JSONArray screen = jO1.getJSONArray("screen");
            ArrayList<String> screens = new ArrayList<>();
            for (int k = 0; k < screen.length(); k++) {
                String pic = screen.getString(k);
                screens.add(pic);
            }

            Log.i("Protocol", info.getPackageName());
            info.setScreen(screens);
            return info;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public String getParms() {
        return "&packageName=" + packageName;
    }
}
