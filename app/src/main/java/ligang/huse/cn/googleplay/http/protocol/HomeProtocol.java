package ligang.huse.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.AppInfo;

/**
 * Created by 2 on 2016/4/20.
 */
public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    ArrayList<AppInfo> appInfos = new ArrayList<>();
    private ArrayList<String> mPicture;

    @Override
    public ArrayList<AppInfo> ParseData(String result) {
        try {
            JSONObject jO = new JSONObject(result);
            JSONArray ja = jO.getJSONArray("list");
            for (int i = 0; i < ja.length(); i++) {
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
            JSONArray ja1 = jO.getJSONArray("picture");
            mPicture = new ArrayList<>();
            for(int k=0;k<ja1.length();k++){
                mPicture.add(ja1.getString(k));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appInfos;
    }

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public String getParms() {
        return "";
    }
    public ArrayList<String>getPicture(){
        return  mPicture;
    }
}
