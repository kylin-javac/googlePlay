package ligang.huse.cn.googleplay.domain;

import java.util.ArrayList;

/**
 * AppIfo首页json的javabean
 */
public class AppInfo {
    /**
     * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、
     * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
     * iconUrl : app/com.youyuan.yyhl/icon.jpg
     * id : 1525490
     * name : 有缘网
     * packageName : com.youyuan.yyhl
     * size : 3876203
     * stars : 4
     */

    private String des;
    private String downloadUrl;
    private String iconUrl;
    private String id;
    private String name;
    private String packageName;
    private long size;
    private double stars;


    //补充字段，供应详情页
    private String author;
    private String date;
    private String downloadNum;
    private String version;
    private ArrayList<SafeInfo> safe;
    private ArrayList<String>screen;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<SafeInfo> getSafe() {
        return safe;
    }

    public void setSafe(ArrayList<SafeInfo> safe) {
        this.safe = safe;
    }

    public ArrayList<String> getScreen() {
        return screen;
    }

    public void setScreen(ArrayList<String> screen) {
        this.screen = screen;
    }

    public static class  SafeInfo{
         private String safedes;
         private String safeDesul;
         private  String safeurl;

        public String getSafe() {
            return safedes;
        }

        public void setSafe(String safe) {
            this.safedes = safe;
        }

        public String getSafeDesul() {
            return safeDesul;
        }

        public void setSafeDesul(String safeDesul) {
            this.safeDesul = safeDesul;
        }

        public String getSafeurl() {
            return safeurl;
        }

        public void setSafeurl(String safeurl) {
            this.safeurl = safeurl;
        }
    }



    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }
}
