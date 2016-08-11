package com.qianfeng.qiongyou.Bean.NextListInfo;

import java.util.List;

/**
 * Created by feng on 2016/3/29.
 */
public class NextListData {
    private List<NextListDataBean> data;

    public List<NextListDataBean> getData() {
        return data;
    }

    public void setData(List<NextListDataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NextListData{" +
                "data=" + data +
                '}';
    }
}
