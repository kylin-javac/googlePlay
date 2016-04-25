package ligang.huse.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 推荐界面的网络解析
 */
public class RecommendProtocol extends BaseProtocol <ArrayList<String>> {

    @Override
    public ArrayList<String> ParseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<String> list = new ArrayList<>();
            for(int i=0;i<ja.length();i++){
                String string = ja.getString(i);
                list.add(string);
            }
            return  list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    public String getParms() {
        return "";
    }
}
