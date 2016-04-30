package ligang.huse.cn.googleplay.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ligang.huse.cn.googleplay.domain.CategoryInfo;

/**
 * 创建时间 javac on 2016/4/28.
 * <p>
 * 文  件 googlePlay
 * <p>
 * 描  述 分类模块的网络数据解析
 */
public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {
    @Override
    public ArrayList<CategoryInfo> ParseData(String result) {
        ArrayList<CategoryInfo> list = new ArrayList<>();
        try {

            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                if (jo.has("title")) {//添加标题
                    CategoryInfo Info = new CategoryInfo();
                    String title = jo.getString("title");
                    Info.setTitle(title);
                    Info.setIsTitle(true);
                    list.add(Info);
                }
                if (jo.has("infos")) {//添加内容
                    JSONArray ja1 = jo.getJSONArray("infos");

                    for (int k = 0; k < ja1.length(); k++) {
                        CategoryInfo info = new CategoryInfo();
                        JSONObject jo1 = ja1.getJSONObject(k);
                        info.setName1(jo1.getString("name1"));
                        info.setName2(jo1.getString("name2"));
                        info.setName3(jo1.getString("name3"));
                        info.setUrl1(jo1.getString("url1"));
                        info.setUrl2(jo1.getString("url2"));
                        info.setUrl3(jo1.getString("url3"));
                        info.setIsTitle(false);
                        list.add(info);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParms() {
        return "";
    }
}
