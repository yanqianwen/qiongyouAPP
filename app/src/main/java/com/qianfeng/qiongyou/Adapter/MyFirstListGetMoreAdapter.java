package com.qianfeng.qiongyou.Adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListTopicListen;
import com.qianfeng.qiongyou.R;

import java.util.List;

/**
 * Created by feng on 2016/3/31.
 */
public class MyFirstListGetMoreAdapter  extends BaseAdapter{
    private List<FirstListTopicListen> mDatas;//热门主题的数据
    private Context mContext;

    public MyFirstListGetMoreAdapter(List<FirstListTopicListen> data, Context context) {
        this.mDatas = data;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_first,parent,false);
            holder=new MyViewHolder();
            holder.simpleDraweeView= (SimpleDraweeView) convertView.findViewById(R.id.image_item_first);
            String url=mDatas.get(position).getImg();
            holder.simpleDraweeView.setImageURI(Uri.parse(url));
        }
        return convertView;
    }
    class MyViewHolder{
        private SimpleDraweeView simpleDraweeView;
    }
}
