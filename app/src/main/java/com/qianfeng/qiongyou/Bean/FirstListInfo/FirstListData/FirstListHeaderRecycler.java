package com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData;

/**
 * Created by feng on 2016/3/30.
 */
public class FirstListHeaderRecycler {
    private String icon;
    private String name;
    private String id;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FirstListHeaderRecycler{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
