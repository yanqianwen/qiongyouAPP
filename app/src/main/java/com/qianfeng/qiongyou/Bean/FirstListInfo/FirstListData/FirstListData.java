package com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData;

/**
 * Created by feng on 2016/3/30.
 */
public class FirstListData {
    private FirstListDataBean data;

    public FirstListDataBean getData() {
        return data;
    }

    public void setData(FirstListDataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FirstListData{" +
                "data=" + data +
                '}';
    }
}
