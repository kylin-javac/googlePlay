package ligang.huse.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.AppInfo;

/**
 * Created by 2 on 2016/4/20.
 */
public class AppProtocol extends BaseProtocol<ArrayList<AppInfo>> {


    @Override
    public ArrayList<AppInfo> ParseData(String result) {
        try {
            ArrayList<AppInfo> appInfos = new ArrayList<>();
            JSONArray ja = new JSONArray(result);
            for(int i=0;i<ja.length();i++){
                JSONObject jO1 = ja.getJSONObject(i);
                AppInfo info = new AppInfo();
                info.setDes(jO1.getString("des"));
                info.setDownloadUrl(jO1.getString("downloadUrl"));
                info.setIconUrl(jO1.getString("iconUrl"));
                info.setId(jO1.getString("id"));
                info.setName(jO1.getString("name"));
                info.setPackageName(jO1.getString("packageName"));
                info.setSize(jO1.getLong("size"));
                info.setStars(jO1.getDouble("stars"));
                appInfos.add(info);
            }

            return appInfos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return  null;
    }

    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParms() {
        return "";
    }
}
