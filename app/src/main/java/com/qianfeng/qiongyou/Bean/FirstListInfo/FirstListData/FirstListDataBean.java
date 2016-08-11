package com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData;

import java.util.List;

/**
 * Created by feng on 2016/3/30.
 */
public class FirstListDataBean {
    private List<FirstListHeaderRecycler> bar;
    private List<FirstListTopic> hot_topic;
    private List<FirstListHeaderParent> promo;
    private List<FirstListRecyclerColor> ptype_icon;
    private List<FirstListTopicListen> slide;

    public List<FirstListHeaderRecycler> getBar() {
        return bar;
    }

    public void setBar(List<FirstListHeaderRecycler> bar) {
        this.bar = bar;
    }

    public List<FirstListTopic> getHot_topic() {
        return hot_topic;
    }

    public void setHot_topic(List<FirstListTopic> hot_topic) {
        this.hot_topic = hot_topic;
    }

    public List<FirstListHeaderParent> getPromo() {
        return promo;
    }

    public void setPromo(List<FirstListHeaderParent> promo) {
        this.promo = promo;
    }

    public List<FirstListRecyclerColor> getPtype_icon() {
        return ptype_icon;
    }

    public void setPtype_icon(List<FirstListRecyclerColor> ptype_icon) {
        this.ptype_icon = ptype_icon;
    }

    public List<FirstListTopicListen> getSlide() {
        return slide;
    }

    public void setSlide(List<FirstListTopicListen> slide) {
        this.slide = slide;
    }

    @Override
    public String toString() {
        return "FirstListDataBean{" +
                "bar=" + bar +
                ", hot_topic=" + hot_topic +
                ", promo=" + promo +
                ", ptype_icon=" + ptype_icon +
                ", slide=" + slide +
                '}';
    }
}
