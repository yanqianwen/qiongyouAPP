package com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData;

/**
 * Created by feng on 2016/3/30.
 */
public class FirstListRecyclerColor {
    private String bgc;
    private String icon;
    private String ptype_id;

    public String getBgc() {
        return bgc;
    }

    public void setBgc(String bgc) {
        this.bgc = bgc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPtype_id() {
        return ptype_id;
    }

    public void setPtype_id(String ptype_id) {
        this.ptype_id = ptype_id;
    }

    @Override
    public String toString() {
        return "FirstListRecyclerColor{" +
                "bgc='" + bgc + '\'' +
                ", icon='" + icon + '\'' +
                ", ptype_id='" + ptype_id + '\'' +
                '}';
    }
}
