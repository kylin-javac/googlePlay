package ligang.huse.cn.googleplay.http.protocol;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ligang.huse.cn.googleplay.http.HttpHelper;
import ligang.huse.cn.googleplay.utils.IOUtils;
import ligang.huse.cn.googleplay.utils.StringUtils;
import ligang.huse.cn.googleplay.utils.UiUitls;

/**
 * 网络封装的基类
 */
public abstract class BaseProtocol<T> {


    public T getData(int index) {//index表示从哪个地方开始获取数据,用于分页

        String result = getCache(index);
        if (StringUtils.isEmpty(result)) {
            result = getDataFromSever(index);
        }
        if (result != null) {
            T data = ParseData(result);
            return data;
        }
        return null;

    }

    public String getDataFromSever(int index) {//index表示从哪个地方开始获取数据,用于分页
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParms());
        if (httpResult != null) {
            Log.i("------------->", httpResult + "");
            String restult = httpResult.getString();
            Log.i("------------->", restult);
            if (!StringUtils.isEmpty(restult)) {
                setCache(index, restult);

            }
            return restult;
        }
        return null;
    }



    //写缓存
    public void setCache(int index, String json) {

        File cacheDir = UiUitls.getContex().getCacheDir();
        File file = new File(cacheDir, getKey() + "?index=" + index + getParms());
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            long dataTime = System.currentTimeMillis() + 30 * 60 * 1000;
            writer.write(dataTime + "\n");
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }


    //读缓存
    public String getCache(int index) {
        File cacheDir = UiUitls.getContex().getCacheDir();
        File file = new File(cacheDir, getKey() + "?index=" + index + getParms());
        if (file.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String dataTime = reader.readLine();
                long aLong = Long.parseLong(dataTime);

                if (System.currentTimeMillis() < aLong) {
                    String line;
                    StringBuffer info = new StringBuffer();
                    while (null != (line = reader.readLine())) {
                        info.append(line);
                    }
                    return info.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    //解析json数据,由子类实现
    public abstract T ParseData(String result);
    //获取网络关键字,由子类实现

    public abstract String getKey();

    //获取网络参数,由子类实现
    public abstract String getParms();


}
