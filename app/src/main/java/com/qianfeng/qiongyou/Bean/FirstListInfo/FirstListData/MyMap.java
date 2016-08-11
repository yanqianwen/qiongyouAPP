package com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by feng on 2016/3/31.
 */
public class MyMap implements Serializable {
    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
