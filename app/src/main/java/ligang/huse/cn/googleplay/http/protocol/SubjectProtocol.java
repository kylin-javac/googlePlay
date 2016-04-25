package ligang.huse.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.SubjectInfo;

/**
 * 解析专题数据
 */
public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    public ArrayList<SubjectInfo> ParseData(String result) {
        try {
            ArrayList<SubjectInfo> list = new ArrayList<>();
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                SubjectInfo info = new SubjectInfo();
                info.setDes(jo.getString("des"));
                info.setUrl(jo.getString("url"));
                list.add(info);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParms() {
        return "";
    }
}
