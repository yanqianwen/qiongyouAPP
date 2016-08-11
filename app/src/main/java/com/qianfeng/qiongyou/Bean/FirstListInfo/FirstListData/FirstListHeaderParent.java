package com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData;

/**
 * Created by feng on 2016/3/30.
 */
public class FirstListHeaderParent {
    private String title;
    private String img;
    private String url;//点击事件

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FirstListHeaderParent{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
